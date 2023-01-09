package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private Long count;
    private MemberEntity memberEntity;
    private String thumbFileName;
    private String thumbFilePath;

    @Builder
    public BoardSaveRequestDto(String title, String content, Long count, MemberEntity memberEntity, String thumbFileName, String thumbFilePath) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.memberEntity = memberEntity;
        this.thumbFileName = thumbFileName;
        this.thumbFilePath = thumbFilePath;
    }

    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .title(title)
                .content(content)
                .count(count)
                .memberEntity(memberEntity)
                .thumbFileName(thumbFileName)
                .thumbFilePath(thumbFilePath)
                .build();
    }
}
