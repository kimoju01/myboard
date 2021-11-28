package com.hyeju.study.myboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String email;

    @Builder
    public BoardUpdateRequestDto(String title, String content, String email) {
        this.title = title;
        this.content = content;
        this.email = email;
    }

}
