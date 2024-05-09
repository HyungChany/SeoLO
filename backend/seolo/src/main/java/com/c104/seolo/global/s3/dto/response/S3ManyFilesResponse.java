package com.c104.seolo.global.s3.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class S3ManyFilesResponse {
    private Map<String, String> urls;
    public S3ManyFilesResponse() {
        urls = new HashMap<>();
    }
    public void addUrl(String key, String url) {
        this.urls.put(key,url);
    }
}
