package com.verda.BE.login.domain.oauth;

// Access Token 으로 요청한 외부 API 프로필 응답값을 우리 서비스의 Model 로 변환시키기 위한 인터페이스
//
// 카카오나 네이버의 email, nickname 정보를 필요로 하기 때문에 Getter 메서드를 추가
public interface OAuthInfoResponse {
    String getEmail();
    String getName();
    String getBirthday();
    String getGender();
    String getAgeRange();
}
