package com.dingdong.picmap.domain.search.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.search.dto.LatLngResponseDto;
import com.dingdong.picmap.domain.search.dto.SearchRequestDto;
import com.dingdong.picmap.domain.search.service.SearchService;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PhotoResponseDto>> searchPhoto(@RequestBody SearchRequestDto requestDto) {
        try {
            return ResponseEntity.ok(searchService.searchPhoto(requestDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}