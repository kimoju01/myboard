package com.hyeju.study.myboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String email;
    private String thumbFileName;
    private String thumbFilePath;

    @Builder
    public BoardUpdateRequestDto(String title, String content, String email, String thumbFileName, String thumbFilePath) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.thumbFileName = thumbFileName;
        this.thumbFilePath = thumbFilePath;
    }

}
