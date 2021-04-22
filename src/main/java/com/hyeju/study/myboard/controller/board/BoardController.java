package com.hyeju.study.myboard.controller.board;

import com.hyeju.study.myboard.dto.BoardDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/board")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    /* 게시글 등록 */
    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/board";
    }

    /* 게시글 detail */
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail.html";
    }

    /* 게시글 삭제 */



}
