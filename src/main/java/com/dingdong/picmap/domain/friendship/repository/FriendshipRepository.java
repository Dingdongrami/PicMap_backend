package com.dingdong.picmap.domain.friendship.repository;

import com.dingdong.picmap.domain.friendship.entity.Friendship;
import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findByRequesterAndReceiver(User requester, User receiver);

    @Query("SELECT f FROM Friendship f "
            + "WHERE f.requester = :user "
            + "OR f.receiver = :user")
    List<Friendship> findAllFriendshipByUser(@Param("user") User user);
}
