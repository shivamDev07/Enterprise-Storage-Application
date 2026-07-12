package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import org.springframework.data.domain.Page;

public interface MetadataService {

    Page<FileMetadata> getAllFiles(int page, int size, String sortBy, String direction);

    FileMetadata getById(String id);

    Page<FileMetadata> searchByFileType(String fileType, int page, int size);

    Page<FileMetadata> searchByFileName(String fileName, int page, int size);

    Page<FileMetadata> searchByBucket(String bucketName, int page, int size);

    Page<FileMetadata> searchByUser(String uploadedBy, int page, int size);

//    Page<FileMetadata> searchByStatus(String status, int page, int size);

    long count();

    long countByBucket(String bucketName);
}
