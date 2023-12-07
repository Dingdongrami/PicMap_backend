package com.dingdong.picmap.domain.friendship.controller;

import com.dingdong.picmap.domain.friendship.dto.FriendshipRequestDto;
import com.dingdong.picmap.domain.friendship.dto.FriendshipResponseDto;
import com.dingdong.picmap.domain.friendship.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponseDto> requestFriendship(@RequestBody FriendshipRequestDto requestDto) {
        return ResponseEntity.ok(friendshipService.requestFriend(requestDto));
    }

    @PutMapping("/accept")
    public ResponseEntity<FriendshipResponseDto> acceptFriendship(@RequestBody FriendshipRequestDto requestDto) {
        return ResponseEntity.ok(friendshipService.acceptFriend(requestDto));
    }

    // 친구 요청 목록
    @GetMapping("/request/{userId}")
    public ResponseEntity<List<FriendshipResponseDto>> getFriendRequestList(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getFriendRequestList(userId));
    }

    // 친구 목록
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendshipResponseDto>> getFriendList(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getFriendList(userId));
    }

    // 요청 거절
    @PutMapping("/reject")
    public ResponseEntity<String> rejectFriendship(@RequestBody FriendshipRequestDto requestDto) {
        return ResponseEntity.ok(friendshipService.rejectFriend(requestDto));
    }

    // 친구 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteFriendship(@RequestBody FriendshipRequestDto requestDto) {
        return ResponseEntity.ok(friendshipService.deleteFriend(requestDto));
    }
}
