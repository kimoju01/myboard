package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardSaveRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
}
