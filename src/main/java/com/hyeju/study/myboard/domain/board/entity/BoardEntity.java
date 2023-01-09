package com.hyeju.study.myboard.domain.board.entity;

import com.hyeju.study.myboard.domain.TimeEntity;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class BoardEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String content;

    @Column(length = 100)
    private Long count;

    private String thumbFileName;

    private String thumbFilePath;

    @ManyToOne  //Many = Board / One = User : 한 명의 멤버는 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name="memId")
    private MemberEntity memberEntity;

    @Builder
    public BoardEntity(String title, String content, Long count, MemberEntity memberEntity, String thumbFileName, String thumbFilePath) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.memberEntity = memberEntity;
        this.thumbFileName = thumbFileName;
        this.thumbFilePath = thumbFilePath;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateWithFile(String title, String content, String thumbFileName, String thumbFilePath) {
        this.title = title;
        this.content = content;
        this.thumbFileName = thumbFileName;
        this.thumbFilePath = thumbFilePath;
    }

    public void upViewCount(Long count) {
        this.count = count;
    }

}
