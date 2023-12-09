package com.dingdong.picmap.domain.circle.repository;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.entity.CircleUser;
import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircleUserRepository extends JpaRepository<CircleUser, Long> {

    @Query("SELECT c FROM Circle c "
            + "INNER JOIN CircleUser cU ON cU.circle = c "
            + "WHERE cU.user = :user")
    List<Circle> findCirclesByUser(@Param("user") User user);

    @Query("SELECT cU FROM CircleUser cU "
            + "WHERE cU.user = :user "
            + "AND cU.circle = :circle")
    Optional<CircleUser> findCircleUserByCircleAndUser(@Param("user") User user, @Param("circle") Circle circle);

    @Query("SELECT u FROM User u "
            + "INNER JOIN CircleUser cU ON cU.user = u "
            + "WHERE cU.circle = :circle")
    List<User> findUsersByCircle(@Param("circle") Circle circle);

    boolean existsByCircleAndUser(Circle circle, User user);
}
