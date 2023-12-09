package com.dingdong.picmap.domain.search.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.search.dto.SearchLocationResponseDto;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final GeoService geoService;
    private final CircleRepository circleRepository;
    private final CircleSharedAlbumRepository circleSharedAlbumRepository;

    public Map<String, LatLng> searchLocationWithBounds(String address) {
        return geoService.getLocationWithBounds(address);
    }

    public List<SearchLocationResponseDto> searchPhoto(String address) {
        Map<String, LatLng> locationWithBounds = geoService.getLocationWithBounds(address);
        LatLng northEast = locationWithBounds.get("northEast");
        LatLng southWest = locationWithBounds.get("southWest");

        List<Circle> publicCircleList = circleRepository.findByStatus("PUBLIC");
        publicCircleList.addAll(circleRepository.findByStatus("GOVERNMENT"));

        List<SearchLocationResponseDto> searchLocationResponseDtoList = new ArrayList<>();

        for (Circle circle : publicCircleList) {
            List<Photo> photoList = circleSharedAlbumRepository.findAllByCircle(circle);
            photoList.stream()
                .filter(photo -> photo.getLongitude() != null && photo.getLatitude() != null)
                .filter(photo -> photo.getLatitude() >= southWest.lat && photo.getLatitude() <= northEast.lat
                    && photo.getLongitude() >= southWest.lng && photo.getLongitude() <= northEast.lng)
                .forEach(photo -> {
                    PhotoResponseDto photoResponseDto = new PhotoResponseDto(photo);
                    searchLocationResponseDtoList.add(new SearchLocationResponseDto(circle, photoResponseDto));
                });
        }
        return searchLocationResponseDtoList;
    }
}
