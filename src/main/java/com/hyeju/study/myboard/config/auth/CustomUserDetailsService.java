package com.hyeju.study.myboard.config.auth;

import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //스프링이 로그인 요청을 가로챌 때, username과 password를 가로채는데 username이 db에 있는지만 확인해주면 됨. 난 username => email이니 email로 찾기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return new CustomUserDetails(memberEntity); //시큐리티의 세션에 유저정보가 저장됨
    }
}