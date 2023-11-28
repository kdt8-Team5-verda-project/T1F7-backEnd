package com.verda.BE.login.domain.oauth;

import org.springframework.util.MultiValueMap;

// 카카오, 네이버 요청에 필요한 데이터를 갖고 있는 파라미터(인터페이스)
//
// 이 인터페이스의 구현체는 Controller 의 @RequestBody 로도 사용하기 때문에 getXXX 라는 네이밍을 사용하지 않아야 함
public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}