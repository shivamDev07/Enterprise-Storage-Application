package com.example.EnterpriseStorageApplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private String endPoints;
    private String accessKey;
    private String secretKey;
    private String region ;

    public String getEndPoints() {
        return endPoints;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getRegion() {
        return region;
    }
}
