package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class PhotoMetadataService {

    public Map<String, Directory> getMetadata(File file) throws ImageProcessingException, IOException {
        Map<String, Directory> map = null;
        Metadata metadata = ImageMetadataReader.readMetadata(file);
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
            map = Map.of("gps", gpsDirectory, "exif", exifSubIFDDirectory);
        }
        return map;
    }
}
