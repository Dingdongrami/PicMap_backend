package com.dingdong.picmap.domain.friendship.entity;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
