package com.example.EnterpriseStorageApplication.repository;

import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FIleMetadataRepository extends MongoRepository<FileMetadata,String> {

    List<FileMetadata> findByUploadedBy(String uploadedBy);

    List<FileMetadata> findByStatus(String status);

    List<FileMetadata> findByBucketName(String bucketName);

    List<FileMetadata> findByOriginalNameContainingIgnoreCase(String name);

    Optional<FileMetadata> findBystoredName(String storedName);
}
