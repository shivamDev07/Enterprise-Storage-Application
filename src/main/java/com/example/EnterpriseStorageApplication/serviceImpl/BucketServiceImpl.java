package com.example.EnterpriseStorageApplication.serviceImpl;

import com.example.EnterpriseStorageApplication.dto.BucketRequest;
import com.example.EnterpriseStorageApplication.exception.BucketNotFoundException;
import com.example.EnterpriseStorageApplication.service.BucketService;

import com.example.EnterpriseStorageApplication.validator.BucketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    private final S3Client s3Client;

    public BucketServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public List<String> listBuckets() {
        ListBucketsResponse response = s3Client.listBuckets();
        List<Bucket> buckets = response.buckets();

        List<String> bucketNames = new ArrayList<>();
        for (Bucket bucket : buckets){
            bucketNames.add(bucket.name());
        }
        return bucketNames;
    }

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            HeadBucketRequest request = HeadBucketRequest.builder().bucket(bucketName).build();
            s3Client.headBucket(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void createBucket(BucketRequest bucketRequest) {
        String bucketName = bucketRequest.getBucketName();
        BucketValidator.validate(bucketName);
        CreateBucketRequest creationRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        s3Client.createBucket(creationRequest);
    }

    @Override
    public void deleteBucket(String bucketName) {
        BucketValidator.validate(bucketName);
        if (!bucketExists(bucketName))
            throw new BucketNotFoundException(bucketName);

        DeleteBucketRequest request = DeleteBucketRequest.builder().bucket(bucketName).build();
        s3Client.deleteBucket(request);
    }
}
