package com.dingdong.picmap.domain.friendship.service;

import com.dingdong.picmap.domain.friendship.dto.FriendshipRequestDto;
import com.dingdong.picmap.domain.friendship.dto.FriendshipResponseDto;
import com.dingdong.picmap.domain.friendship.entity.Friendship;
import com.dingdong.picmap.domain.friendship.repository.FriendshipRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    // 친구 요청
    public FriendshipResponseDto requestFriend(FriendshipRequestDto requestDto) {
        User requester = userRepository.findById(requestDto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (requester.equals(receiver)) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

         Friendship friendship = new Friendship(requester, receiver);
         friendshipRepository.save(friendship);
         receiver.getReceivedFriendships().add(friendship);
         log.info("{}", receiver.getReceivedFriendships().get(0));
         return new FriendshipResponseDto(friendship);
    }
}
