package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long count;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MemberEntity memberEntity;
    private String thumbFileName;
    private String thumbFilePath;

    public BoardResponseDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.count = boardEntity.getCount();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.createdDate = boardEntity.getCreatedDate();
        this.modifiedDate = boardEntity.getModifiedDate();
        this.memberEntity = boardEntity.getMemberEntity();
        this.thumbFileName = boardEntity.getThumbFileName();
        this.thumbFilePath = boardEntity.getThumbFilePath();
    }

}
