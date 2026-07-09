package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.dto.CopyRequest;
import com.example.EnterpriseStorageApplication.dto.DownloadResponse;
import com.example.EnterpriseStorageApplication.dto.MoveRequest;
import com.example.EnterpriseStorageApplication.dto.RenameRequest;
import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ObjectService {

    FileMetadata upload(MultipartFile file, String bucketName, String uploadBy);

    DownloadResponse download(String metadataId);

    void delete(String metadataId);

    List<String> listObjects(String bucketName);

    void copy(CopyRequest copyRequest);

    void move(MoveRequest moveRequest);

    void rename(RenameRequest renameRequest);
}
