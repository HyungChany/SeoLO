package com.c104.seolo.global.s3.controller;


import com.c104.seolo.global.s3.dto.response.S3ManyFilesResponse;
import com.c104.seolo.global.s3.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AmazonS3Controller {
    private final AmazonS3Service amazonS3Service;

    @PostMapping("")
    public ResponseEntity<S3ManyFilesResponse> uploadFile(List<MultipartFile> multipartFiles){
        return ResponseEntity.ok(amazonS3Service.uploadFiles(multipartFiles));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName){
        amazonS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}
