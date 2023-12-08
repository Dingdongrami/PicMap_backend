package com.dingdong.picmap.domain.search.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GeoService {

    private final String apiKey;
    private final GeoApiContext geoApiContext;

    public GeoService(@Value("${google.maps.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public LatLng getCoordinates(String address) {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).awaitIgnoreError();
        if (results != null && results.length > 0) {
            return results[0].geometry.location;
        }
        return null;
    }

    public Map<String, LatLng> getLocationWithBounds(String address) {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).awaitIgnoreError();
        if (results != null && results.length > 0) {
            LatLng center = results[0].geometry.location;

            // 동서남북 좌표 계산
            LatLng northEast = calculateCoordinate(center, 45);
            LatLng southWest = calculateCoordinate(center, 225);
            LatLng northWest = calculateCoordinate(center, 315);
            LatLng southEast = calculateCoordinate(center, 135);

            return Map.of("center", center, "northEast", northEast, "southWest", southWest, "northWest", northWest, "southEast", southEast);
        }
        return null;
    }

    // 중심 좌표(center)에서 일정 거리와 방향에 따른 좌표 계산
    private LatLng calculateCoordinate(LatLng center, double bearing) {
        double earthRadius = 6371.0; // 지구 반지름 (단위: km)
        double angularDistance = 2 / earthRadius;

        double lat1 = Math.toRadians(center.lat);
        double lng1 = Math.toRadians(center.lng);
        double angularBearing = Math.toRadians(bearing);

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(angularDistance) +
                Math.cos(lat1) * Math.sin(angularDistance) * Math.cos(angularBearing));

        double lng2 = lng1 + Math.atan2(
                Math.sin(angularBearing) * Math.sin(angularDistance) * Math.cos(lat1),
                Math.cos(angularDistance) - Math.sin(lat1) * Math.sin(lat2)
        );

        return new LatLng(Math.toDegrees(lat2), Math.toDegrees(lng2));
    }
}
