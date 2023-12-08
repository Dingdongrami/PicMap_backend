package com.dingdong.picmap.domain.search.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.search.dto.LatLngResponseDto;
import com.dingdong.picmap.domain.search.service.SearchService;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/bound")
    public ResponseEntity<LatLngResponseDto> searchLocation(@RequestParam String address) {
        try {
            Map<String, LatLng> bounds = searchService.searchLocationWithBounds(address);
            return ResponseEntity.ok(new LatLngResponseDto(bounds));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/location")
    public ResponseEntity<List<PhotoResponseDto>> searchPhoto(@RequestParam String address) {
        try {
            return ResponseEntity.ok(searchService.searchPhoto(address));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}