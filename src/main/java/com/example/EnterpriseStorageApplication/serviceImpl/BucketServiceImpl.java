package com.example.EnterpriseStorageApplication.serviceImpl;

import com.example.EnterpriseStorageApplication.config.S3Config;
import com.example.EnterpriseStorageApplication.service.BucketService;
import kotlin.collections.ArrayDeque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private S3Client s3Client;

    @Override
    public List<String> listBuckets() {
        ListBucketsResponse response = s3Client.listBuckets();
        List<Bucket> buckets = response.buckets();

        List<String> bucketNames = new ArrayList<>();
        for (Bucket b : buckets){
            bucketNames.add(b.name());
        }
        return bucketNames;
    }
}
