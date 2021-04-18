package com.hyeju.study.myboard.domain.repository;

import com.hyeju.study.myboard.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
