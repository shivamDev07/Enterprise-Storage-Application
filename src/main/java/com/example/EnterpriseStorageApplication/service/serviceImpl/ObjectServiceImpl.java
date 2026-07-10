package com.example.EnterpriseStorageApplication.service.serviceImpl;

import com.example.EnterpriseStorageApplication.dto.CopyRequest;
import com.example.EnterpriseStorageApplication.dto.DownloadResponse;
import com.example.EnterpriseStorageApplication.dto.MoveRequest;
import com.example.EnterpriseStorageApplication.dto.RenameRequest;
import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import com.example.EnterpriseStorageApplication.exception.FileDownloadException;
import com.example.EnterpriseStorageApplication.exception.FileUploadException;
import com.example.EnterpriseStorageApplication.exception.MetadataNotFoundException;
import com.example.EnterpriseStorageApplication.repository.FIleMetadataRepository;
import com.example.EnterpriseStorageApplication.service.ObjectService;
import com.example.EnterpriseStorageApplication.util.ChecksumUtil;
import com.example.EnterpriseStorageApplication.util.FileNameGenerator;
import com.example.EnterpriseStorageApplication.util.FileStatus;
import com.example.EnterpriseStorageApplication.util.FileTypeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectServiceImpl implements ObjectService {

    private final S3Client s3Client;
    private final FIleMetadataRepository repository;

    public ObjectServiceImpl(S3Client s3Client, FIleMetadataRepository repository) {
        this.s3Client = s3Client;
        this.repository = repository;
    }

    @Override
    public FileMetadata upload(MultipartFile file, String bucketName, String uploadBy) {

        try {
            String originalName = file.getOriginalFilename();
            String storedName = FileNameGenerator.generate(originalName);
            String checkSum = ChecksumUtil.sha256(file.getInputStream());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(storedName)
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

            FileMetadata metaData = new FileMetadata();
            metaData.setOriginalName(originalName);
            metaData.setStoredName(storedName);
            metaData.setBucketName(bucketName);
            metaData.setFileSize(file.getSize());
            metaData.setContentType(file.getContentType());
            metaData.setUploadedBy(uploadBy);
            metaData.setUploadedAt(Instant.now());
            metaData.setChecksum(checkSum);
            metaData.setEtag(response.eTag());
            metaData.setStatus(FileStatus.ACTIVE);
            metaData.setFileType(FileTypeUtil.determineType(file.getContentType()));

            return repository.save(metaData);

        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    @Override
    public DownloadResponse download(String metadataId) {

        FileMetadata metadata = repository.findById(metadataId)
                .orElseThrow(() ->
                        new MetadataNotFoundException(metadataId));

        InputStream stream = getObject(metadata);

        return new DownloadResponse(
                metadata.getOriginalName(),
                stream
        );
    }

    private InputStream getObject(FileMetadata metadata) {

        try {

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(metadata.getBucketName())
                    .key(metadata.getStoredName())
                    .build();

            return s3Client.getObject(request);

        } catch (Exception ex) {
            throw new FileDownloadException(ex.getMessage());
        }
    }

    @Override
    public List<String> listObjects(String bucketName) {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> objects = new ArrayList<>();
        for (S3Object obj : response.contents()){
            objects.add(obj.key());
        }

        return objects;
    }

    @Override
    public void delete(String metadataId) {
        FileMetadata metadata = repository.findById(metadataId).orElseThrow(() -> new MetadataNotFoundException(metadataId));
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(metadata.getBucketName())
                .key(metadata.getStoredName())
                .build();

        s3Client.deleteObject(request);
        repository.delete(metadata);
    }

    @Override
    public void copy(CopyRequest request) {
        CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                .sourceBucket(request.getSourceBucket())
                .sourceKey(request.getObjectKey())
                .destinationBucket(request.getTargetBucket())
                .destinationKey(request.getObjectKey())
                .build();

        s3Client.copyObject(copyRequest);
    }

    @Override
    public void move(MoveRequest request) {
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setSourceBucket(request.getSourceBucket());
        copyRequest.setTargetBucket(request.getTargetBucket());
        copyRequest.setObjectKey(request.getObjectKey());

        copy(copyRequest);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(request.getSourceBucket())
                .key(request.getObjectKey())
                .build();

        s3Client.deleteObject(deleteRequest);

        FileMetadata metaData = repository.findBystoredName(request.getObjectKey())
                .orElseThrow(() -> new MetadataNotFoundException(request.getObjectKey()));

        metaData.setBucketName(request.getTargetBucket());
        repository.save(metaData);
    }

    @Override
    public void rename(RenameRequest request) {
        CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                .sourceBucket(request.getBucketName())
                .sourceKey(request.getOldName())
                .destinationBucket(request.getBucketName())
                .destinationKey(request.getNewName())
                .build();
        s3Client.copyObject(copyRequest);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder().bucket(request.getBucketName()).key(request.getOldName()).build();
        s3Client.deleteObject(deleteRequest);
    }
}
