package com.yuyun.choiceapp.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String nickname;
    private String email;
    private String password;

    private String authCode;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String username, String nickname, String email, String password, Authority authority,
                  String authCode, MemberStatus status) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.authCode = authCode;
        this.status = status;
    }
}
