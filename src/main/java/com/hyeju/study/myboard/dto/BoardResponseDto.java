package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardResponseDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.createdDate = boardEntity.getCreatedDate();
        this.modifiedDate = boardEntity.getModifiedDate();
    }

}
