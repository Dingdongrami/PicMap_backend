package com.dingdong.picmap.domain.photo.service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) {
        dirName = dirName + "/";
        String fileName = createFileName(multipartFile.getOriginalFilename(), dirName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        putS3(multipartFile, fileName, objectMetadata);
        removeNewFile(convert(multipartFile));
        log.info("upload - fileName: {}", fileName);
        return fileName;
    }

    private String createFileName(String originalFileName, String dirName) {
        return dirName + UUID.randomUUID() + getFileExtension(originalFileName);
    }

    // 파일 확장자 가져오기
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일입니다. [%s]", fileName));
        }
    }

    private void putS3(MultipartFile uploadFile, String fileName, ObjectMetadata objectMetadata) {
        log.info("putS3 - uploadFile: {}, fileName: {}", uploadFile, fileName);

        try(InputStream inputStream = uploadFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 업로드 중 에러가 발생하였습니다. [%s]", fileName));
        }
    }

    private File convert(MultipartFile image) {
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            log.error("파일 변환 실패", e);
        }
        return file;
    }

    private void removeNewFile(File file) {
        if(file.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
}
