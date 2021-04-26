package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.dto.BoardSaveRequestDto;
import com.hyeju.study.myboard.dto.BoardUpdateRequestDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // Post-Create, Get-Read, Put-Update, Delete-Delete
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    /* 게시글 등록 */
    @PostMapping("/api/v1/post")
    public Long save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    /* 게시글 수정 */
    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    /* 게시글 detail */
    @GetMapping("/api/v1/post/{id}")
    public BoardResponseDto findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

}
