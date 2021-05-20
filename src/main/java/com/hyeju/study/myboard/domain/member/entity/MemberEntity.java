package com.hyeju.study.myboard.domain.member.entity;

import com.hyeju.study.myboard.domain.TimeEntity;
import com.hyeju.study.myboard.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column
//    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public MemberEntity(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

//    public MemberEntity update(String name, String picture) {
//        this.name = name;
//        this.picture = picture;
//
//        return this;
//    }

    public String getRoleKey() {
        return this.role.getKey();
    }   //"ROLE_" 을 붙여서 return 해주어야 하기 때문에 key값에 "ROLE_" 붙여둠
}
