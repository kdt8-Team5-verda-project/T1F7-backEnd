package com.verda.BE.login.member.domain;

import com.verda.BE.login.domain.oauth.OAuthProvider;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원 정보를 담는 Member entity
//
// Email, Nickname 과 같은 프로필 정보나 인증 타입을 갖고 있음
//
// 프로젝트 성격에 따라 회원 (Member) 도메인과 인증 (Authentication) 도메인을 분리하는 경우도 있으니 이 부분은 설계하기에 따라 바뀔 수 있음
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String name;
    private String birthday;
    private String gender;
    private String investmentPropensity;
    private String number;

    @Builder
    public UserEntity(long userId, String email, String name, String birthday, String gender, String location,
                      String investmentPropensity, String number, OAuthProvider oAuthProvider) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.number = number;
    }
}
