package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.member.Role;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.Getter;

@Getter
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String picture;
    private Role role;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

}
