package com.dingdong.picmap.domain.friendship.entity;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name = "friendship")
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    public Friendship(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = FriendshipStatus.REQUESTED;
    }

    // 친구인 경우 true 반환
    public boolean isFriend() {
        return this.status == FriendshipStatus.ACCEPTED;
    }

}
