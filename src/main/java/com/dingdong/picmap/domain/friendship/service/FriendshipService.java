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

import java.util.List;
import java.util.stream.Collectors;

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

        // 이미 친구 요청을 보낸 경우
        if (friendshipRepository.findByRequesterAndReceiver(requester, receiver) != null
        || friendshipRepository.findByRequesterAndReceiver(receiver, requester) != null) {
            throw new IllegalArgumentException("이미 친구 요청을 보냈습니다.");
        }

         Friendship friendship = new Friendship(requester, receiver);
         friendshipRepository.save(friendship);
         receiver.getReceivedFriendships().add(friendship);
         return new FriendshipResponseDto(friendship);
    }

    // 요청 수락
    public FriendshipResponseDto acceptFriend(FriendshipRequestDto requestDto) {
        User requester = userRepository.findById(requestDto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver);

        checkRequest(friendship);
        checkFriendship(friendship);

        friendship.accept();
        receiver.getReceivedFriendships().remove(friendship);
        friendshipRepository.save(friendship);
        return new FriendshipResponseDto(friendship);
    }

    // 친구 요청 목록
    public List<FriendshipResponseDto> getFriendRequestList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return FriendshipResponseDto.listOf(user.getReceivedFriendships());
    }

    // 친구 목록
    public List<FriendshipResponseDto> getFriendList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<Friendship> friendships = friendshipRepository.findAllFriendshipByUser(user)
                .stream()
                .filter(Friendship::isFriend)
                .collect(Collectors.toList());
        return FriendshipResponseDto.listOf(friendships);
    }

    // 요청 거절
    public String rejectFriend(FriendshipRequestDto requestDto) {
        User requester = userRepository.findById(requestDto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver);
        checkRequest(friendship);
        checkFriendship(friendship);
        receiver.getReceivedFriendships().remove(friendship);
        friendshipRepository.delete(friendship);
        return "친구 요청을 거절했습니다.";
    }

    public String deleteFriend(FriendshipRequestDto requestDto) {
        User requester = userRepository.findById(requestDto.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver);
        checkRequest(friendship);
        friendshipRepository.delete(friendship);
        return "친구를 삭제했습니다.";
    }

    public void checkRequest(Friendship friendship) {
        if (friendship == null) {
            throw new IllegalArgumentException("친구 요청을 먼저 보내야 합니다.");
        }
    }

    public void checkFriendship(Friendship friendship) {
        if (friendship.isFriend()) {
            throw new IllegalArgumentException("이미 친구입니다.");
        }
    }

}
