package com.dingdong.picmap.domain.photo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class PhotoDownloadService {

    private final S3Downloader s3Downloader;

    public ResponseEntity<byte[]> download(String filePath) throws Exception {
        byte[] bytes = s3Downloader.download(filePath);
        HttpHeaders httpHeaders = setHttpHeaders(filePath, bytes);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private HttpHeaders setHttpHeaders(String filePath, byte[] bytes) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType(filePath));
        httpHeaders.setContentLength(bytes.length);
        String[] arr = filePath.split("/");
        String type = arr[arr.length - 1];
        String fileName = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return httpHeaders;
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
