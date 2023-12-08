package com.dingdong.picmap.domain.search.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.photo.service.PhotoService;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<PhotoResponseDto> searchPhoto(String address) {
        Map<String, LatLng> locationWithBounds = geoService.getLocationWithBounds(address);
        LatLng northEast = locationWithBounds.get("northEast");
        LatLng southWest = locationWithBounds.get("southWest");

        List<Circle> publicCircleList = circleRepository.findByStatus("PUBLIC");
        List<Circle> governmentCircleList = circleRepository.findByStatus("GOVERNMENT");

        List<Photo> photoList = publicCircleList.stream()
                .map(circleSharedAlbumRepository::findAllByCircle)
                .flatMap(List::stream)
                .filter(photo -> photo.getLatitude() != null && photo.getLongitude() != null)
                .filter(photo -> photo.getLatitude() <= northEast.lat && photo.getLongitude() >= southWest.lng
                        && photo.getLatitude() >= southWest.lat && photo.getLongitude() <= northEast.lng)
                .collect(Collectors.toList());

        governmentCircleList.stream()
                .map(circleSharedAlbumRepository::findAllByCircle)
                .flatMap(List::stream)
                .filter(photo -> photo.getLatitude() != null && photo.getLongitude() != null)
                .filter(photo -> photo.getLatitude() <= northEast.lat && photo.getLongitude() >= southWest.lng
                        && photo.getLatitude() >= southWest.lat && photo.getLongitude() <= northEast.lng)
                .forEach(photoList::add);

        return photoList.stream()
                .map(PhotoResponseDto::of)
                .collect(Collectors.toList());
    }
}
