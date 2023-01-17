package com.hyeju.study.myboard.service.board;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class S3Uploader {

    /* Spring Boot Cloud AWS 사용하면 S3 관련 Bean 자동 생성해줌 */
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /* MultipartFile 전달 받아서 S3에 전달하기 위해 File로 전환 */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return uploadToS3(uploadFile, dirName);
    }

    /* 전환된 File을 S3에 public 읽기 권한으로 put */
    public String uploadToS3(File uploadFile, String dirName) {
        String origFileName = uploadFile.getName();
        String extension = origFileName.substring(origFileName.lastIndexOf("."));
//        String fileName = dirName + "/" + uploadFile.getName();
        String fileName = dirName + "/" + UUID.randomUUID() + extension;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    /* 업로드 된 File의 S3 url 반환 */
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /* 로컬에 생성된 File 삭제 (convert 과정에서 로컬에 파일 생성된 것 삭제) */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("파일이 삭제되었습니다.");
        } else {
            System.out.println("파일이 삭제되지 못했습니다");
        }
    }

    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            File convertFile = new File(multipartFile.getOriginalFilename());
            if (convertFile.createNewFile()) {  // 파일 성공적으로 생성하면 true
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    // FileOutputStream 두 번째 파라미터가 true면 기존 파일이 있을 경우 기존 파일에 내용 이어붙임.
                    // false면 기존 파일에 덮어써버림. 파라미터 입력하지 않았을 경우 기본 값은 false
                    fos.write(multipartFile.getBytes());
                }
                return Optional.of(convertFile);
            }
        }
        return Optional.empty();
    }


}
