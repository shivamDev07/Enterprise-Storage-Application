package com.example.EnterpriseStorageApplication.service;

import com.example.EnterpriseStorageApplication.dto.response.LifecycleResponse;
import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import org.springframework.data.domain.Page;

public interface LifecycleService {

    LifecycleResponse softDelete(String id);

    LifecycleResponse restore(String id);

    LifecycleResponse archive(String id);

    LifecycleResponse permanentDelete(String id);

    Page<FileMetadata> getActiveFiles(int page, int size);

    Page<FileMetadata> getArchivedFiles(int page, int size);

    Page<FileMetadata> getDeletedFiles(int page, int size);
}
