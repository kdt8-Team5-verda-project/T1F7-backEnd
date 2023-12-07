package org.example.kakao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.oAuth.OAuthLoginParams;
import org.example.oAuth.OAuthProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

// 카카오 API 요청에 필요한 authorizationCode 를 갖고 있는 클래스
@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider()  {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}