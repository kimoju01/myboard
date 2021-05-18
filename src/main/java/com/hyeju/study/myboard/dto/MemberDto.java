package com.hyeju.study.myboard.dto;

import com.hyeju.study.myboard.domain.member.Role;
import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String email;
    private String password;
//    private String picture;
    private Role role;

    @Builder
    public MemberDto(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .name(name)
                .email(email)
                .password(password)
//                .picture(picture)
                .role(Role.USER)
                .build();
    }

}
