package com.example.EnterpriseStorageApplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bucket_audit")
public class bucketAudit {

    @Id
    private String id;

    private String bucketName;

    private String operation;

    private Long timeStamp;

    public bucketAudit() {}

    public bucketAudit(String id, String bucketName, String operation, Long timeStamp) {
        this.id = id;
        this.bucketName = bucketName;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
