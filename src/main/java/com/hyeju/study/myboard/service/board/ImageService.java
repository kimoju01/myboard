package com.hyeju.study.myboard.service.board;

import com.google.gson.JsonObject;
import com.hyeju.study.myboard.domain.board.repository.ImageRepository;
import com.hyeju.study.myboard.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public JsonObject saveSummernoteImage(MultipartFile multipartFile) throws Exception {

        String fileRoot = "C:\\summernote_image\\";
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명

        // 이미지 정보 DB 저장
        ImageDto imageDto = new ImageDto();

        try {
            imageDto.setOrigFileName(originalFileName);
            imageDto.setFileName(savedFileName);
            imageDto.setContentType(multipartFile.getContentType());
            imageDto.setFileSize(multipartFile.getSize());

            imageRepository.save(imageDto.toEntity());

        } catch (Exception e) {
            throw new Exception("Failed to store file " + multipartFile.getOriginalFilename(), e);
        }

        File targetFile = new File(fileRoot + savedFileName);

        JsonObject jsonObject = new JsonObject();

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName); // key, value 추가
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();

        }

        return jsonObject;

    }
}

