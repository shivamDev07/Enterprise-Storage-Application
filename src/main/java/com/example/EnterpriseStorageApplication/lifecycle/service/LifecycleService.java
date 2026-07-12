package com.example.EnterpriseStorageApplication.lifecycle.service;

import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import com.example.EnterpriseStorageApplication.lifecycle.dto.LifecycleResponse;
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
