package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.dto.request.CopyRequest;
import com.example.EnterpriseStorageApplication.dto.request.MoveRequest;
import com.example.EnterpriseStorageApplication.dto.request.RenameRequest;
import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ObjectService {

    FileMetadata upload(
            MultipartFile file,
            String bucketName,
            String uploadedBy);

    InputStream download(String metadataId);

    void delete(String metadataId);

    List<String> listObjects(String bucketName);

    void copy(CopyRequest request);

    void move(MoveRequest request);

    void rename(RenameRequest request);
}
