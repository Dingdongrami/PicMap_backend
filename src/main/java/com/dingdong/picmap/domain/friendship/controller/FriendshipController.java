package com.dingdong.picmap.domain.friendship.controller;

import com.dingdong.picmap.domain.friendship.dto.FriendshipRequestDto;
import com.dingdong.picmap.domain.friendship.dto.FriendshipResponseDto;
import com.dingdong.picmap.domain.friendship.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponseDto> requestFriendship(@RequestBody FriendshipRequestDto requestDto) {
        return ResponseEntity.ok(friendshipService.requestFriend(requestDto));
    }
}
