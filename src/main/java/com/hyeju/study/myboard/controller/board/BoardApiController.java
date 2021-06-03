package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.config.auth.CustomUserDetails;
import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.dto.BoardSaveRequestDto;
import com.hyeju.study.myboard.dto.BoardUpdateRequestDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController // Post-Create, Get-Read, Put-Update, Delete-Delete
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class BoardApiController {

    private final BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/posts")
    public Page<BoardResponseDto> list(Pageable pageable) {
        return boardService.findAll(pageable);
    }

    /* 게시글 등록 */
    @PostMapping("/posts")
    public Long save(@RequestBody BoardSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return boardService.save(requestDto, customUserDetails.getMemberEntity());
    }

    /* 게시글 수정 */
    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
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
