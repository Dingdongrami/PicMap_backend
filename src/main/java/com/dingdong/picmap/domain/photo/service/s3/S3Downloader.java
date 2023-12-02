package com.dingdong.picmap.domain.photo.service.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@Service
@RequiredArgsConstructor
public class S3Downloader {

    private final AmazonS3Client amazonS3Client;

      public byte[] download(String filePath) throws IOException {
          String bucket = "picmapbucket";
          S3Object s3Object = amazonS3Client.getObject(bucket, filePath);
          S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
          return IOUtils.toByteArray(objectInputStream);
      }
}
