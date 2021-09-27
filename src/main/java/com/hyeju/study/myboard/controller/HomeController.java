package com.hyeju.study.myboard.controller;

import com.hyeju.study.myboard.config.auth.KakaoOAuthService;
import com.hyeju.study.myboard.config.auth.KakaoUserInfo;
import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("recentPostList", boardService.getRecentPost());
        return "index";
    }

    @GetMapping("/posts")
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boardList", boardService.listPost(pageable, keyword));
        model.addAttribute("maxPage", 5);
        return "board/list";
    }

    @GetMapping("/posts/{id}")
    public String postsDetail(@PathVariable Long id, Model model) {
        BoardResponseDto responseDto = boardService.findById(id);
        model.addAttribute("board", responseDto);
        model.addAttribute("recentPostList", boardService.getRecentPost());
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

    @GetMapping("/oauth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code) {
        String accessToken = kakaoOAuthService.getAccessToken(code);
        // 넘어온 코드 값으로 액세스 토큰 얻고
        KakaoUserInfo kakaoUserInfo = kakaoOAuthService.getKakaoUserInfo(accessToken);
        // 그 액세스 토큰으로 유저 정보 얻고
        kakaoOAuthService.saveKakaoUser(kakaoUserInfo);
        // 유저 정보로 회원가입 및 로그인 시킴
        return "redirect:/";
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
