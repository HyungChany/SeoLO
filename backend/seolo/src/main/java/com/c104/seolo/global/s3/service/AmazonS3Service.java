package com.c104.seolo.global.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.s3.dto.response.S3ManyFilesResponse;
import com.c104.seolo.global.s3.dto.response.S3OneFileResponse;
import com.c104.seolo.global.s3.exception.S3ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AmazonS3Service {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    // 썸네일 하나만 받는 경우
    public S3OneFileResponse uploadThunmail(MultipartFile multipartFile) {
        // Step 1: 파일의 존재와 비어 있지 않은지 먼저 확인
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new CommonException(S3ErrorCode.EMPTY_UPLOAD_FILE);
        }
        // Step 2: 업로드 로직 진행 (확장자 검증 포함)
        return new S3OneFileResponse(uploadToS3ReturnURL(multipartFile, 0));
    }

    // 여러개의 파일을 받는 경우
    public S3ManyFilesResponse uploadFiles(List<MultipartFile> multipartFiles){
        // Step 1: 파일의 존재와 비어 있지 않은지 먼저 확인
        if (multipartFiles.get(0) == null || multipartFiles.get(0).isEmpty()) {
            throw new CommonException(S3ErrorCode.EMPTY_UPLOAD_FILE);
        }


        S3ManyFilesResponse response = new S3ManyFilesResponse();

        // 파일을 순차적으로 처리하고, 각 파일에 대한 URL을 수집
        IntStream.range(0, multipartFiles.size())
                .forEach(i -> {
                    MultipartFile file = multipartFiles.get(i);
                    // Step 2: 업로드 로직 진행 (확장자 검증 포함)
                    String url = uploadToS3ReturnURL(file, i); // 파일을 S3에 업로드하고, 생성된 URL을 반환
                    response.addUrl("url" + (i + 1), url); // 맵에 URL 추가
                });

        return response;
    }

    // 인덱스 번호로 구분하여 S3에 업로드
    // 업로드 된 객체에 접근할 수 있는 url 반환
    private String uploadToS3ReturnURL(MultipartFile file, int index) {
        // 파일명 생성 시 확장자 검증 포함
        String UUIDFileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, UUIDFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            log.info("파일 업로드 : [{}] to [{}] ", file.getOriginalFilename(), UUIDFileName);
            return getS3FileURL(UUIDFileName);
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }

    // S3 저장된 파일명을 통해 S3 객체 url 조회
    public String getS3FileURL(String hashedFileName) {
        return amazonS3.getUrl(bucket, hashedFileName).toString();
    }

    // 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
    public String createFileName(String originalFileName){
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    // 확장자 추출
    private String getFileExtension(String fileName){
        // 허용하는 파일 확장자 목록
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".svg");

        if (fileName.lastIndexOf("." ) == -1) {
            throw new CommonException(S3ErrorCode.INVALID_EXTENSION);
        }

        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new CommonException(S3ErrorCode.INVALID_EXTENSION);
        }

        return extension;
    }


    public void deleteFile(String fileName){
        try {
            String path = new URL(fileName).getPath();
            String S3key = path.substring(1);

            amazonS3.deleteObject(new DeleteObjectRequest(bucket, S3key));
            log.info("파일 S3에서 삭제 : [{} : {}] ", bucket, S3key);
        } catch (MalformedURLException e) {
            log.error("잘못된 URL 형식 : {}", fileName, e);
            throw new RuntimeException();
        }

    }
}
