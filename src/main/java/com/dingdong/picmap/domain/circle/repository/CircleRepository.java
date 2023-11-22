package com.dingdong.picmap.domain.circle.repository;

import com.dingdong.picmap.domain.circle.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CircleRepository extends JpaRepository<Circle, Long> {

    List<Circle> findByStatus(String status);
}
