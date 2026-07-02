package com.example.EnterpriseStorageApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BucketAuditRepository extends MongoRepository<BucketAuditRepository,String> {

}
