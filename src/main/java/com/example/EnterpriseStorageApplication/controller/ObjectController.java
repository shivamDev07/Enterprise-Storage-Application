package com.example.EnterpriseStorageApplication.controller;

import com.example.EnterpriseStorageApplication.dto.CopyRequest;
import com.example.EnterpriseStorageApplication.dto.DownloadResponse;
import com.example.EnterpriseStorageApplication.dto.MoveRequest;
import com.example.EnterpriseStorageApplication.dto.RenameRequest;
import com.example.EnterpriseStorageApplication.entity.FileMetadata;
import com.example.EnterpriseStorageApplication.service.ObjectService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/objects")
public class ObjectController {

    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }

    @PostMapping("/upload")
    public FileMetadata upload(
            @RequestParam("file")MultipartFile file,
            @RequestParam("bucketName")String bucketName,
            @RequestParam("uploadedBy")String uploadBy
            ){
        return objectService.upload(file, bucketName, uploadBy);
    }

    @GetMapping("/download/{metadataId}")
    public ResponseEntity<InputStreamResource> download(
            @PathVariable String metadataId) {

        DownloadResponse response = objectService.download(metadataId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                response.getOriginalFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(response.getInputStream()));
    }

    @GetMapping
    public List<String> listObjects(@RequestParam("bucketName")String bucketName){
        return objectService.listObjects(bucketName);
    }

    @DeleteMapping("/{metadataId}")
    public String delete(@PathVariable("metadataId") String metadataId){
        objectService.delete(metadataId);
        return "Object Deleted :- " + metadataId;
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copy(@Valid @RequestBody CopyRequest request) {
        objectService.copy(request);

        return ResponseEntity.ok("Copied Successfully");
    }

    @PostMapping("/move")
    public ResponseEntity<String> move(@Valid @RequestBody MoveRequest request) {
        objectService.move(request);
        return ResponseEntity.ok("Moved Successfully");
    }

    @PostMapping("/rename")
    public ResponseEntity<String> rename(@Valid @RequestBody RenameRequest request) {
        objectService.rename(request);
        return ResponseEntity.ok("Renamed Successfully");
    }
}
