package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.config.auth.CustomUserDetails;
import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.dto.BoardSaveRequestDto;
import com.hyeju.study.myboard.dto.BoardUpdateRequestDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController // Post-Create, Get-Read, Put-Update, Delete-Delete
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class BoardApiController {

    private final BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/posts")
    public Page<BoardResponseDto> list(Pageable pageable, String keyword) {
        return boardService.listPost(pageable, keyword);
    }

    /* 게시글 등록 */
    @PostMapping("/posts")
//    public Long save(@RequestBody BoardSaveRequestDto requestDto,
    // file은 JSON에 포함될 수 없고 JSON, file을 포함한 다중 요청 처리하기 위해 @RequestPart 사용
    public Long save(@RequestPart(value = "boardInfo") BoardSaveRequestDto requestDto,
                     @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                     @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        return boardService.save(requestDto, multipartFile, customUserDetails.getMemberEntity());
    }

    /* 게시글 수정 */
    @PreAuthorize("#requestDto.email == principal.username")
    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "boardInfo") BoardUpdateRequestDto requestDto,
                       @RequestPart(value = "oldThumbFileName", required = false) String oldThumbFileName,
                       @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return boardService.update(id, requestDto, oldThumbFileName, multipartFile);
    }

    /* 게시글 삭제 */
    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    /* 게시글 detail */
    @GetMapping("/posts/{id}")
    public BoardResponseDto findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

}
