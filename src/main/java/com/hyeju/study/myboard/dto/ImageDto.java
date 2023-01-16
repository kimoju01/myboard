package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.board.entity.ImageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {
//    private String origFileName;
//    private String fileName;
    private String filePath;
//    private String contentType;
//    private Long fileSize;

    /* AWS S3 환경 */
    @Builder
    public ImageDto(String filePath) {
        this.filePath = filePath;
    }

    public ImageEntity toEntity() {
        return ImageEntity.builder()
                .filePath(filePath)
                .build();
    }

    /* 로컬 환경 */
//    @Builder
//    public ImageDto(String origFileName, String fileName, String filePath, String contentType, Long fileSize) {
//        this.origFileName = origFileName;
//        this.fileName = fileName;
//        this.filePath = filePath;
//        this.contentType = contentType;
//        this.fileSize = fileSize;
//    }
//
//    public ImageEntity toEntity() {
//        return ImageEntity.builder()
//                .origFileName(origFileName)
//                .fileName(fileName)
//                .filePath(filePath)
//                .contentType(contentType)
//                .fileSize(fileSize)
//                .build();
//    }

}
