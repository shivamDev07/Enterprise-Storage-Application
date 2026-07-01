package com.example.EnterpriseStorageApplication.exception;

public class InvalidBucketException extends StorageException{
    public InvalidBucketException(String message) {
        super(message);
    }
}
