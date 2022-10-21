package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.service.board.ImageService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;

    @PostMapping(value = "/image", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> uploadSummernoteImage(@RequestParam("file") MultipartFile multipartFile) {
        try {
            return ResponseEntity.ok(imageService.saveSummernoteImage(multipartFile));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
