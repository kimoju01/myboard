package com.hyeju.study.myboard.domain.board.repository;

import com.hyeju.study.myboard.domain.board.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
