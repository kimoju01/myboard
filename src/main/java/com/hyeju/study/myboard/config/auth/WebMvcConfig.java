package com.hyeju.study.myboard.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 외부 경로 리소스를 url로 바로 불러올 수 있도록 설정
    // localhost:8080/summernoteImage/1111.jpg 로 접속하면 .../Summernote_image/1111.jpa 파일 불러온다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/summernoteImage/**")
//                .addResourceLocations("file:///C:/Study/Project_documents/myboard_documents/Summernote_image/");
                .addResourceLocations("https://my-hyeju-board-bucket.s3.ap-northeast-2.amazonaws.com/SummernoteImage/");
        registry.addResourceHandler("/thumbnailImage/**")
//                .addResourceLocations("file:///C:/Study/Project_documents/myboard_documents/Thumbnail_Image/");
                .addResourceLocations("https://my-hyeju-board-bucket.s3.ap-northeast-2.amazonaws.com/ThumbnailImage/");
    }
}
