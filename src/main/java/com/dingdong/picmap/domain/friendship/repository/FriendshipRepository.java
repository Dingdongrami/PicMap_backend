package com.dingdong.picmap.domain.friendship.repository;

import com.dingdong.picmap.domain.friendship.entity.Friendship;
import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findByRequesterAndReceiver(User requester, User receiver);
}
