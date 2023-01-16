package com.hyeju.study.myboard.domain.board.entity;

import com.hyeju.study.myboard.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class ImageEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String origFileName;

//    @Column(nullable = false)
//    private String fileName;

    @Column(nullable = false)
    private String filePath;

//    @Column(nullable = false)
//    private String contentType;

//    private Long fileSize;

    @Builder
    public ImageEntity(String filePath) {
        this.filePath = filePath;
    }

    /* 로컬 환경 */
//    @Builder
//    public ImageEntity(String origFileName, String fileName, String filePath, String contentType, Long fileSize) {
//        this.origFileName = origFileName;
//        this.fileName = fileName;
//        this.filePath = filePath;
//        this.contentType = contentType;
//        this.fileSize = fileSize;
//
//    }


}
