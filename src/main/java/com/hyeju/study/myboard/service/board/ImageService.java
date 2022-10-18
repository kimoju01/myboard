package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.board.entity.ImageEntity;
import com.hyeju.study.myboard.domain.board.repository.ImageRepository;
import com.hyeju.study.myboard.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Long upload(MultipartFile multipartFile) throws Exception {
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명

        ImageDto imageDto = new ImageDto();

        try {
            imageDto.setOrigFileName(originalFileName);
            imageDto.setFileName(savedFileName);
            imageDto.setContentType(multipartFile.getContentType());
            imageDto.setFileSize(multipartFile.getSize());

            return imageRepository.save(imageDto.toEntity()).getId();

        } catch (Exception e) {
            throw new Exception("Failed to store file " + multipartFile.getOriginalFilename(), e);

        }
    }
}
