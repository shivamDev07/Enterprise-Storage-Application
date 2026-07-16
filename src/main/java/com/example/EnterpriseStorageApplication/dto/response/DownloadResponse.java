package com.example.EnterpriseStorageApplication.dto.response;

import java.io.InputStream;

public class DownloadResponse {

    private String originalFileName;

    private InputStream inputStream;

    public DownloadResponse(String originalFileName, InputStream inputStream) {
        this.originalFileName = originalFileName;
        this.inputStream = inputStream;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
