package com.hyeju.study.myboard.controller;

import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index() {
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
    public @ResponseBody String kakaoCallback(String code) {
        // Post 방식으로 key=value 데이터를 카카오에게 요청 => RestTemplate 라이브러리 사용
        RestTemplate rt = new RestTemplate();
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 내가 지금 전송할 HTTP Body 데이터가 key=value 형태라고 알려준다

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // Body 데이터를 담는다
        params.add("grant_type", "authorization_code");
        params.add("client_id", "192ae19481f623fcfb044a05f17176cb");
        params.add("redirect_uri", "http://localhost:8080/oauth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,  //HttpEntity 오브젝트
                String.class
        );

        return "카카오 로그인 토큰 요청 완료 : " + response;
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
