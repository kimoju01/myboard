package com.hyeju.study.myboard.domain.board.repository;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
