package com.verda.BE.login.infra.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.verda.BE.login.domain.oauth.OAuthInfoResponse;
import com.verda.BE.login.domain.oauth.OAuthProvider;
import lombok.Getter;

// https://kapi.kakao.com/v2/user/me 요청 결과값
//
// Kakao Developers - 사용자 정보 가져오기 를 참고해서 만든 응답값
//
// 원래 더 많은 응답값이 오지만 필요한 데이터만 추려내기 위해 @JsonIgnoreProperties(ignoreUnknown = true) 를 사용
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
        private String birthday;
        private String gender;
        private String age_range;

    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getName() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getBirthday() {
        return kakaoAccount.birthday;
    }

    @Override
    public String getGender() {
        return kakaoAccount.gender;
    }
    @Override
    public String getAgeRange() {
        return kakaoAccount.age_range;
    }

    @Override
    public String getNumber() {
        return null;
    }
}