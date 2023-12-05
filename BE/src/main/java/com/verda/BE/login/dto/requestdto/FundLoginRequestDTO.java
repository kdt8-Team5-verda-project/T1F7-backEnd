package com.verda.BE.login.dto.requestdto;

import com.verda.BE.login.domain.AuthTokens;
import com.verda.BE.login.member.domain.FundEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundLoginRequestDTO {
    private boolean isExistUser;
    private AuthTokens authToken;
    private String email;
    private FundEntity fundEntity;
    private String jwtToken; // JWT 토큰 추가
    private String subject;  // subject 추가
    private Date expiredAt;  // expiredAt 추가
}