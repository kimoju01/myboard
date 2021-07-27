package com.hyeju.study.myboard.domain.board.repository;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /* 제목 또는 내용을 대소문자 구분 없이 검색 */
    Page<BoardEntity> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
    List<BoardEntity> findTop3ByOrderByIdDesc();
}
