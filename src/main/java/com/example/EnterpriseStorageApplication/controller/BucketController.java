package com.example.EnterpriseStorageApplication.controller;

import com.example.EnterpriseStorageApplication.dto.BucketRequest;
import com.example.EnterpriseStorageApplication.service.BucketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/buckets")
@RestController
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping
    public void createBucket(@RequestBody BucketRequest bucketRequest){
        bucketService.createBucket(bucketRequest);
    }

    @GetMapping
    public List<String> listBuckets(){
        return bucketService.listBuckets();
    }
}
