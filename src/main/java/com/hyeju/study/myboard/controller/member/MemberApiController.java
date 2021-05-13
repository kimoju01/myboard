package com.hyeju.study.myboard.controller.member;

import com.hyeju.study.myboard.dto.MemberDto;
import com.hyeju.study.myboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Post-Create, Get-Read, Put-Update, Delete-Delete
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/members/new")
    public Long save(@RequestBody MemberDto memberDto) {
        return memberService.save(memberDto);
    }

//    @PostMapping("/members/login")
//    public MemberDto login(@RequestBody MemberDto memberDto) {
//
//    }

}
