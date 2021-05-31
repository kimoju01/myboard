package com.hyeju.study.myboard.controller;

import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String list(Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boardList", boardService.findAll(pageable));
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

    @GetMapping("/register")
    public String usersRegister() {
        return "board/join";
    }

    @GetMapping("/login")
    public String usersLogin() {
        return "board/login";
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
