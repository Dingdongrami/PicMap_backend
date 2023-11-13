package com.dingdong.picmap.domain.photo.service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile 을 전달받아 File 로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        log.info("upload (1) - multipartFile: {}", multipartFile);
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    public String upload(File uploadFile, String dirName) {
        log.info("upload (2) - uploadFile: {}", uploadFile);
        String fileName = dirName + "/" + uploadFile.getName();
        return putS3(uploadFile, fileName);
    }

    private String putS3(File uploadFile, String fileName) {
        log.info("putS3 - uploadFile: {}, fileName: {}", uploadFile, fileName);
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public Optional<File> convert(MultipartFile file) throws  IOException {
        log.info("convert - file: {}", file);
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public Map<String, Directory> readMetadata(File uploadFile) throws ImageProcessingException, IOException {
        log.info("readMetadata - uploadFile: {}", uploadFile);
        Metadata metadata = ImageMetadataReader.readMetadata(uploadFile);
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (gpsDirectory != null) {
            log.info("위도 : {}", gpsDirectory.getGeoLocation().getLatitude());
            log.info("경도 : {}", gpsDirectory.getGeoLocation().getLongitude());
        }
        if (exifSubIFDDirectory != null) {
            log.info("촬영일시 : {}", exifSubIFDDirectory.getDateOriginal());
            log.info("촬영장비 : {}", exifSubIFDDirectory.getDescription(ExifSubIFDDirectory.TAG_LENS_MODEL));
        }
        if (exifSubIFDDirectory != null && gpsDirectory != null) {
            Map<String, Directory> map = Map.of("gps", gpsDirectory, "exif", exifSubIFDDirectory);
        }
        return null;
    }
}
