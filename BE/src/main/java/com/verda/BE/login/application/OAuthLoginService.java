package com.verda.BE.login.application;

import com.verda.BE.login.domain.AuthTokens;
import com.verda.BE.login.domain.AuthTokensGenerator;
import com.verda.BE.login.domain.oauth.OAuthInfoResponse;
import com.verda.BE.login.domain.oauth.OAuthLoginParams;
import com.verda.BE.login.domain.oauth.RequestOAuthInfoService;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.UserEntity;
import com.verda.BE.login.member.domain.KakaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

//        System.out.println("Login successful for memberId: {}" + memberId);
//        return authTokensGenerator.generate(memberId);
        AuthTokens authTokens = authTokensGenerator.generate(memberId);
        System.out.println("AccessToken: " + authTokens.getAccessToken());

        return authTokens;

    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return kakaoRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(UserEntity::getUserId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        UserEntity userEntity = UserEntity.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getName())
                .birthday(oAuthInfoResponse.getBirthday())
                .gender(oAuthInfoResponse.getGender())
                .age_range(oAuthInfoResponse.getAgeRange())
                .build();

        return kakaoRepository.save(userEntity).getUserId();
    }

    private Long newFund(OAuthInfoResponse oAuthInfoResponse) {
        FundEntity fundEntity = FundEntity.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getName())
                .age_range(oAuthInfoResponse.getAgeRange())
                .build();

        return fundRepository.save(fundEntity).getFmId();
    }
}