package com.hyeju.study.myboard.service.member;

import com.hyeju.study.myboard.domain.member.Role;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import com.hyeju.study.myboard.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
//public class MemberService implements UserDetailsService {
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberDto memberDto) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println("암호화 하기 전 비번 : " + memberDto.getPassword());
////        String encodePassword = encoder.encode(memberDto.getPassword());
////        MemberEntity memberEntity = MemberEntity.builder()
////                .email(memberDto.getEmail())
////                .name(memberDto.getName())
////                .password(encodePassword)
////                .build();
////        return memberRepository.save(memberEntity).getId();
//        memberDto.setPassword(encoder.encode(memberDto.getPassword()));
//        System.out.println("암호화 후 비번 : " + memberDto.getPassword());

        return memberRepository.save(memberDto.toEntity()).getId();
    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        MemberEntity memberEntity = memberRepository.findByEmail(username)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
//
////        return new User(memberEntity.getEmail(), memberEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRoleKey())));
//
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey()));
////                return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
////                return Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRoleKey()));
//            }
//
//            @Override
//            public String getUsername() {
//                return memberEntity.getEmail();
//            }
//
//            @Override
//            public String getPassword() {
//                return memberEntity.getPassword();
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return true;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return true;
//            }
//        };
//
//        return userDetails;
//
//    }
}