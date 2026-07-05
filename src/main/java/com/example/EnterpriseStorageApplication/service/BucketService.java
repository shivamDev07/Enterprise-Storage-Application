package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.dto.BucketRequest;

import java.util.List;

public interface BucketService {
    List<String> listBuckets();
    boolean bucketExists(String bucketName);
    void createBucket(BucketRequest bucketRequest);
    void deleteBucket(String bucketName);
}
