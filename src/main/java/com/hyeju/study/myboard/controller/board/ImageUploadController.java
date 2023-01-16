package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.service.board.ImageService;
import com.hyeju.study.myboard.service.board.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;

    private final S3Uploader s3Uploader;

    @PostMapping(value = "/image", produces = "application/json")
    @ResponseBody
    /* S3 업로드 */
    public String uploadSummernoteImage(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return imageService.saveSummernoteImage(multipartFile);
//        return s3Uploader.upload(multipartFile, "SummernoteImage");
    }

    /* 로컬 환경 업로드 */
//    public ResponseEntity<?> uploadSummernoteImage(@RequestParam("file") MultipartFile multipartFile) {
//        try {
//            return ResponseEntity.ok(imageService.saveSummernoteImage(multipartFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//
//    }

}
