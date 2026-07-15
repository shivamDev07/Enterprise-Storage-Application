package com.example.EnterpriseStorageApplication.controller;

import com.example.EnterpriseStorageApplication.dto.BucketRequest;
import com.example.EnterpriseStorageApplication.service.BucketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createBucket(@Valid @RequestBody BucketRequest request) {
        bucketService.createBucket(request.getBucketName());
        return ResponseEntity.ok("Bucket created successfully");
    }

    @DeleteMapping("/{bucketName}")
    public ResponseEntity<String> deleteBucket(@PathVariable String bucketName) {
        bucketService.deleteBucket(bucketName);
        return ResponseEntity.ok("Bucket deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<String>> getBuckets() {
        return ResponseEntity.ok(bucketService.getAllBuckets());
    }
}
