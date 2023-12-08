package com.dingdong.picmap.domain.search.dto;

import com.google.maps.model.LatLng;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class LatLngResponseDto {

    private Double centerLat;
    private Double centerLng;
    private Double northEastLat;
    private Double northEastLng;
    private Double southWestLat;
    private Double southWestLng;
    private Double northWestLat;
    private Double northWestLng;
    private Double southEastLat;
    private Double southEastLng;

    public LatLngResponseDto(Map<String, LatLng> bounds) {
        LatLng center = bounds.get("center");
        LatLng northEast = bounds.get("northEast");
        LatLng southWest = bounds.get("southWest");
        LatLng northWest = bounds.get("northWest");
        LatLng southEast = bounds.get("southEast");

        this.centerLat = center.lat;
        this.centerLng = center.lng;
        this.northEastLat = northEast.lat;
        this.northEastLng = northEast.lng;
        this.southWestLat = southWest.lat;
        this.southWestLng = southWest.lng;
        this.northWestLat = northWest.lat;
        this.northWestLng = northWest.lng;
        this.southEastLat = southEast.lat;
        this.southEastLng = southEast.lng;
    }
}
