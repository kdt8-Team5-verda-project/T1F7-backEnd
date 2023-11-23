package com.verda.BE.login.infra.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Authorization Code 를 기반으로 타플랫폼 Access Token 을 받아오기 위한 Response Model
//
// 여러 가지 값을 받아오지만 여기서 사용할 부분은 Access Token만 사용
// https://kauth.kakao.com/oauth/token 요청 결과값
//
// Kakao Developers - 카카오 로그인 토큰 받기 의 응답값 부분을 참고
@Getter
@NoArgsConstructor
public class KakaoTokens {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private String refreshTokenExpiresIn;

    @JsonProperty("scope")
    private String scope;
}
