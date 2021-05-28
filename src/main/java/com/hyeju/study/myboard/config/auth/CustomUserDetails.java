package com.hyeju.study.myboard.config.auth;

import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//스프링 시큐리티가 로그인 요청을 가로채서(/loginProc) 로그인을 진행하고 완료되면 UserDetails 타입 오브젝트를
//스프링 시큐리티의 고유한 세션저장소에 저장한다.
@Getter
public class CustomUserDetails implements UserDetails {

    private MemberEntity memberEntity;  //콤포지션 (MemberEntity를 품고있다.

    public CustomUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    @Override
    public String getUsername() {
        return memberEntity.getEmail();
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    //계정이 갖고있는 권한 목록을 리턴 (여러개 -> for문 돌려야함)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRoleKey()));
    }

    @Override
    public boolean isAccountNonExpired() {  //계정이 만료되지 않았는가
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   //계정이 잠겨있지 않는가
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //비밀번호가 만료되지 않았는가
        return true;
    }

    @Override
    public boolean isEnabled() {    //계정이 활성화 상태인가
        return true;
    }
}
