package com.dingdong.picmap.domain.friendship.dto;

import com.dingdong.picmap.domain.friendship.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResponseDto {

    private Long friendshipId;
    private Long requesterId;
    private Long receiverId;
    private String status;

    public FriendshipResponseDto(Friendship friendship) {
        this.friendshipId = friendship.getId();
        this.requesterId = friendship.getRequester().getId();
        this.receiverId = friendship.getReceiver().getId();
        this.status = friendship.getStatus().toString();
    }

    public static List<FriendshipResponseDto> listOf(List<Friendship> friendships) {
        return friendships.stream()
                .map(FriendshipResponseDto::new)
                .collect(Collectors.toList());
    }
}
