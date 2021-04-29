package com.hyeju.study.myboard.controller;

import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts")
    public String list(Model model) {
        model.addAttribute("boardList", boardService.findAll());
        return "board/list";
    }

    @GetMapping("/posts/{id}")
    public String postsDetail(@PathVariable Long id, Model model) {
        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);
        return "board/detail";
    }

    @GetMapping("/posts/save")
    public String postsWrite() {
        return "board/write";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);
        return "board/update";
    }

    @GetMapping("destination-single")
    public String destination_single() {
        return "destination-single";
    }

    @GetMapping("hotel")
    public String hotel() {
        return "hotel";
    }

    @GetMapping("hotel-single")
    public String hotel_single() {
        return "hotel-single";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }



}
