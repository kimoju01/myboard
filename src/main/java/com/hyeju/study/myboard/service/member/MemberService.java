package com.hyeju.study.myboard.service.member;

import com.hyeju.study.myboard.domain.member.Role;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import com.hyeju.study.myboard.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return new User(memberEntity.getEmail(), memberEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRoleKey())));


    }
}