package com.example.EnterpriseStorageApplication.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Client {
    private final StorageProperties storageProperties;

    public S3Client(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }
}
