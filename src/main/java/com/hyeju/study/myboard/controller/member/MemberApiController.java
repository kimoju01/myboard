package com.hyeju.study.myboard.controller.member;

import com.hyeju.study.myboard.dto.MemberDto;
import com.hyeju.study.myboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController // Post-Create, Get-Read, Put-Update, Delete-Delete
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    /* 멤버 회원 가입 */
    @PostMapping("/members")
    public Long save(@RequestBody MemberDto memberDto) {
        return memberService.save(memberDto);
    }


}
