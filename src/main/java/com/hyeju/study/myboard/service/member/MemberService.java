package com.hyeju.study.myboard.service.member;

import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import com.hyeju.study.myboard.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long save(MemberDto memberDto) {
        String encodePassword = encoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodePassword);
        return memberRepository.save(memberDto.toEntity()).getId();
    }

    public boolean isDuplicateEmail(String email) {
        return memberRepository.existsMemberEntityByEmail(email);
    }
}