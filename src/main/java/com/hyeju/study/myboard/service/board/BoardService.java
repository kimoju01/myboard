package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import com.hyeju.study.myboard.domain.board.repository.BoardRepository;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.dto.BoardSaveRequestDto;
import com.hyeju.study.myboard.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;

    /* 게시글 등록 (S3) */
    @Transactional
    public Long save(BoardSaveRequestDto requestDto, MultipartFile multipartFile, MemberEntity memberEntity) throws IOException {
        requestDto.setCount(0L);
        requestDto.setMemberEntity(memberEntity);

        if (multipartFile != null) {
            String uploadImageUrl = s3Uploader.upload(multipartFile, "ThumbnailImage");

            // url => [https://버킷 주소/버킷 폴더 이름/파일명]
            String savedFileName = uploadImageUrl.substring(uploadImageUrl.lastIndexOf("/") + 1);
            requestDto.setThumbFileName(savedFileName);
            requestDto.setThumbFilePath(uploadImageUrl);
        }

        return boardRepository.save(requestDto.toEntity()).getId();
    }

    /* 게시글 등록 (로컬) */
//    @Transactional
//    public Long save(BoardSaveRequestDto requestDto, MultipartFile multipartFile, MemberEntity memberEntity) throws IOException {
//        requestDto.setCount(0L);
//        requestDto.setMemberEntity(memberEntity);
//
//        if (multipartFile != null) {    // 왜 isEmpty()는 안 됐지?
//            String fileRoot = "C:\\Study\\Project_documents\\myboard_documents\\Thumbnail_Image\\";
//            String originalFileName = multipartFile.getOriginalFilename();
//            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            String savedFileName = UUID.randomUUID() + extension;
//
//            File saveFile = new File(fileRoot, savedFileName);
//            multipartFile.transferTo(saveFile);
//
//            requestDto.setThumbFileName(savedFileName);
//            requestDto.setThumbFilePath(fileRoot + savedFileName);
//        }
//
//        return boardRepository.save(requestDto.toEntity()).getId();
//    }

    /* 게시글 수정 (S3) */
    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto, String oldThumbFileName, MultipartFile multipartFile) throws IOException {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        if (multipartFile != null && multipartFile.getSize() > 0) {  // 새로운 파일이 전송된 경우 (사이즈가 0보다 큰 경우) => != null 조건 없으면 에러나네
            if (oldThumbFileName != null) {  // 기존에 썸네일 이미지가 존재했던 경우
                // 기존 존재하던 썸네일 이미지 삭제
                s3Uploader.removeObject("/ThumbnailImage/" + oldThumbFileName);
                System.out.println(oldThumbFileName);
            }

            // 새 썸네일 이미지 저장
            String uploadImageUrl = s3Uploader.upload(multipartFile, "ThumbnailImage");

            String savedFileName = uploadImageUrl.substring(uploadImageUrl.lastIndexOf("/") + 1);
            requestDto.setThumbFileName(savedFileName);
            requestDto.setThumbFilePath(uploadImageUrl);

            boardEntity.updateWithFile(requestDto.getTitle(), requestDto.getContent(), requestDto.getThumbFileName(), requestDto.getThumbFilePath());
        }

        // 파일이 전송되지 않았을 경우엔 제목과 글만 저장
        boardEntity.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /* 게시글 수정 (로컬) */
//    @Transactional
////    public Long update(Long id, BoardUpdateRequestDto requestDto) {
//    public Long update(Long id, BoardUpdateRequestDto requestDto, String oldThumbFileName, MultipartFile multipartFile) throws IOException {
//        BoardEntity boardEntity = boardRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));
//
//        if (multipartFile != null && multipartFile.getSize() > 0) {  // 새로운 파일이 전송된 경우 (사이즈가 0보다 큰 경우) => != null 조건 없으면 에러나네
//
//            String fileRoot = "C:\\Study\\Project_documents\\myboard_documents\\Thumbnail_Image\\";
//            String originalFileName = multipartFile.getOriginalFilename();
//            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            String savedFileName = UUID.randomUUID() + extension;
//
//            if (oldThumbFileName != null) {  // 기존에 썸네일 이미지가 존재했던 경우
//                // 기존 존재하던 썸네일 이미지 삭제
//                File oldFile = new File(fileRoot + oldThumbFileName);
//                if (oldFile.exists()) {
//                    oldFile.delete();
//                }
//            }
//
//            // 새 썸네일 이미지 저장
//            File saveFile = new File(fileRoot, savedFileName);
//            multipartFile.transferTo(saveFile);
//            requestDto.setThumbFileName(savedFileName);
//            requestDto.setThumbFilePath(fileRoot + savedFileName);
//
//            boardEntity.updateWithFile(requestDto.getTitle(), requestDto.getContent(), requestDto.getThumbFileName(), requestDto.getThumbFilePath());
//        }
//
//        // 파일이 전송되지 않았을 경우엔 제목과 글만 저장
//        boardEntity.update(requestDto.getTitle(), requestDto.getContent());
//
//        return id;
//    }

    /* 게시글 삭제 (S3) */
    @Transactional
    public void delete(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        // 썸네일 이미지가 존재한다면 이미지도 같이 삭제
        if (boardEntity.getThumbFilePath() != null) {
            s3Uploader.removeObject("/ThumbnailImage/" + boardEntity.getThumbFileName());
        }

        boardRepository.delete(boardEntity);
    }


    /*게시글 삭제 (로컬) */
//    @Transactional
//    public void delete(Long id) {
//        BoardEntity boardEntity = boardRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));
//
//        // 썸네일 이미지가 존재한다면 이미지도 같이 삭제
//        if (boardEntity.getThumbFilePath() != null) {
//            File file = new File(boardEntity.getThumbFilePath());
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//
//        boardRepository.delete(boardEntity);
//    }

    /* 게시글 조회 */
    @Transactional
    public BoardResponseDto findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));
        boardEntity.upViewCount(boardEntity.getCount() + 1);

        return new BoardResponseDto(boardEntity);
    }

    /* 게시글 전체&검색 목록 */
    @Transactional(readOnly = true)
    public Page<BoardResponseDto> listPost(Pageable pageable, String keyword) {
        Page<BoardEntity> boardEntityList =
                keyword == null
                ? boardRepository.findAll(pageable)
                : boardRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
        return boardEntityList.map(boardEntity -> new BoardResponseDto(boardEntity)); // = return boardEntityList.map(BoardResponseDto::new);
    }

    /* 최근 3개 게시글 받아오기 */
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getRecentPost() {
        List<BoardEntity> boardEntityList = boardRepository.findTop3ByOrderByIdDesc();
        return boardEntityList.stream().map(boardEntity -> new BoardResponseDto(boardEntity)).collect(Collectors.toList());
    }
}
