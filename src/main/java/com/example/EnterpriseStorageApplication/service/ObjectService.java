package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.dto.CopyRequest;
import com.example.EnterpriseStorageApplication.dto.MoveRequest;
import com.example.EnterpriseStorageApplication.dto.RenameRequest;

public interface ObjectService {

//    FileMetadata upload(MultipartFile file, String bucketName, String uploadBy);

//    InputStream download(String metadataId);

//    void delete(String metadataId);

//    List<String> listObjects(String bucketName);

    void copy(CopyRequest copyRequest);

    void move(MoveRequest moveRequest);

    void rename(RenameRequest renameRequest);
}
