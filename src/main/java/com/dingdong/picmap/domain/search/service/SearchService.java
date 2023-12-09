package com.dingdong.picmap.domain.search.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.search.dto.SearchRequestDto;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CircleUserRepository circleUserRepository;

    public Map<String, LatLng> searchLocationWithBounds(String address) {
        return geoService.getLocationWithBounds(address);
    }

    public List<PhotoResponseDto> searchPhoto(SearchRequestDto requestDto) {
        Map<String, LatLng> locationWithBounds = geoService.getLocationWithBounds(requestDto.getAddress());
        LatLng northEast = locationWithBounds.get("northEast");
        LatLng southWest = locationWithBounds.get("southWest");

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        List<Circle> publicCircleList = circleUserRepository.findCirclesByUser(user)
                .stream()
                .filter(circle -> circle.getStatus().equals("PUBLIC") || circle.getStatus().equals("GOVERNMENT"))
                .collect(Collectors.toList());

        List<Photo> photoList = publicCircleList.stream()
                .map(circleSharedAlbumRepository::findAllByCircle)
                .flatMap(List::stream)
                .filter(photo -> photo.getLatitude() != null && photo.getLongitude() != null)
                .filter(photo -> photo.getLatitude() <= northEast.lat && photo.getLongitude() >= southWest.lng
                        && photo.getLatitude() >= southWest.lat && photo.getLongitude() <= northEast.lng)
                .collect(Collectors.toList());

        return photoList.stream()
                .map(PhotoResponseDto::of)
                .collect(Collectors.toList());
    }
}
