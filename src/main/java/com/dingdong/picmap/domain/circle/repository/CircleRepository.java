package com.dingdong.picmap.domain.circle.repository;

import com.dingdong.picmap.domain.circle.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {

    List<Circle> findByStatus(String status);

    @Query("SELECT c FROM Circle c "
            + "WHERE c.status = 'PUBLIC' "
            + "OR c.status = 'GOVERNMENT'")
    List<Circle> findPublicAndGovernmentCircles();
}
