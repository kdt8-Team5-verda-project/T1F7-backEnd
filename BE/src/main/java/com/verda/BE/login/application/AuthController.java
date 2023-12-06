package com.verda.BE.login.application;

import com.verda.BE.login.domain.AuthTokens;
import com.verda.BE.login.dto.requestdto.FundLoginRequestDTO;
import com.verda.BE.login.dto.requestdto.UserLoginRequestDTO;
import com.verda.BE.login.infra.JwtTokenProvider;
import com.verda.BE.login.infra.kakao.KakaoLoginParams;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.KakaoRepository;
import com.verda.BE.login.member.domain.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    @PostMapping("/kakaouser")
    @Operation(summary = "유저 로그인", description = "유저 카카오 로그인")

    public ResponseEntity<UserLoginRequestDTO> loginKakaoUser(@RequestBody KakaoLoginParams params) {

        try {
            AuthTokens authTokens = oAuthLoginService.UserLogin(params);

            Optional<UserEntity> userEntity = kakaoRepository.findByEmail(authTokens.getEmail());
            boolean isExistingUser = kakaoRepository.findByEmail(authTokens.getEmail()).isPresent();

            UserLoginRequestDTO responseUser = new UserLoginRequestDTO(isExistingUser, authTokens, authTokens.getEmail());
            return ResponseEntity.ok(responseUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/kakaofund")
    @Operation(summary = "펀드매니저 로그인", description = "펀드 매니저 카카오 로그인")

    public ResponseEntity<FundLoginRequestDTO> loginKakaofund(@RequestBody KakaoLoginParams params) {

        try {
            AuthTokens authTokens = oAuthLoginService.FundLogin(params);

            Optional<FundEntity> fundEntity = fundRepository.findByEmail(authTokens.getEmail());
            boolean isExistingFund = fundRepository.findByEmail(authTokens.getEmail()).isPresent();

            //JWT 생성
            String jwtToken = jwtTokenProvider.generateFund(fundEntity.get().getFmId(), authTokens.getEmail(), fundEntity.get().getName(), new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRE_TIME));

            //suject와 expiredAt 필드를 적절한 값으로 설정해야 함
            String subject = "subject";
            Date expiredAt = new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRE_TIME);

            FundLoginRequestDTO responseFund = new FundLoginRequestDTO(isExistingFund, authTokens, authTokens.getEmail(), fundEntity.get(), jwtToken, subject, expiredAt);
            responseFund.setJwtToken(jwtToken);
            return ResponseEntity.ok(responseFund);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
