package com.verda.BE.login.member.application;

import com.google.protobuf.Api;
import com.verda.BE.common.ErrorCode;
import com.verda.BE.common.JwtDecode;
import com.verda.BE.exception.ApiException;
import com.verda.BE.login.domain.AuthTokensGenerator;
import com.verda.BE.login.dto.requestdto.*;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.UserEntity;
import com.verda.BE.login.member.domain.KakaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
//    private final AuthTokensGenerator authTokensGenerator;
    private final MemberService memberService;
    private final JwtDecode jwtDecode;

    @GetMapping("/user")
    @Operation(summary = "유저 목록 조회", description = "유저 목록을 조회합니다.")
    public ResponseEntity<List<UserEntity>> findAllUsers() {
        return ResponseEntity.ok(kakaoRepository.findAll());
    }

    @GetMapping("/fund")
    @Operation(summary = "펀드 매니저 목록 조회", description = "펀드 매니저 목록을 조회합니다.")
    public ResponseEntity<List<FundEntity>> findAllFunds() {
        return ResponseEntity.ok(fundRepository.findAll());
    }

    //    @GetMapping("/user/{accessToken}")
//    @Operation(summary = "특정 유저 조회", description = "특정 유저를 조회함.")
//    public ResponseEntity<UserEntity> findByAccessToken(@PathVariable(name = "accessToken") String accessToken) {
//        Long memberId = authTokensGenerator.extractMemberId(accessToken);
//        return ResponseEntity.ok(kakaoRepository.findById(memberId).get());
//    }
    @GetMapping("/find/user")
    @Operation(summary = "액세스 토큰을 기반으로 사용자 정보 가져오기", description = "제공된 액세스 토큰을 기반으로 사용자 정보를 가져옵니다.")
    public ResponseEntity<UserEntity> findByAccessToken(@RequestHeader("Authorization") String accessToken) {
//        try {
//            // MemberService의 extractAccessToken 메서드를 사용하여 토큰 추출
//            String token = memberService.extractAccessToken(accessToken);
//
//            // 토큰에서 회원 ID 추출
//            Long memberId = authTokensGenerator.extractMemberId(token);
//
//            // 회원 ID를 기반으로 사용자 정보 가져오기
//            Optional<UserEntity> userEntityOptional = kakaoRepository.findById(memberId);
//
//            return userEntityOptional.map(ResponseEntity::ok)
//                    .orElse(ResponseEntity.notFound().build());
//        } catch (ApiException e) {
//            // ApiException이 발생한 경우 예외 처리
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
        try {
            //토큰에서 회원 ID 추출
            Long memberId = memberService.extractMemberId(accessToken);

            //회원 ID를 기반으로 사용자 정보 가져오기
            return ResponseEntity.ok(kakaoRepository.findById(memberId).orElse(null));
        } catch (ApiException e) {
            //ApiException이 발생한 경우 예외 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user/exist/{email}")
    @Operation(summary = "유저 판별 확인", description = "유저의 추가정보가 있는지 없는지 확인합니다.")
    public boolean AddUserInfo(
            @PathVariable(name = "email") String email) {
        System.out.println("email : " + email);
        return memberService.isExistUserAddInfo(email);
    }

    @GetMapping("/fund/exist/{email}")
    @Operation(summary = "펀드매니저 판별 확인", description = "펀드의 추가정보가 있는지 없는지 확인합니다.")
    public boolean AddFundInfo(
            @PathVariable(name = "email") String email) {
        return memberService.isExistFundAddInfo(email);
    }

    @PostMapping("/user/add")
    @Operation(summary = "유저 추가 정보 저장", description = "유저의 추가정보를 저장합니다.")
    public void addUserInfo(@RequestBody UserAddInfoRequestDTO userAddInfoRequestDTO) {
        memberService.saveUserAddInfo(userAddInfoRequestDTO);

    }


    @PostMapping("/fund/add")
    @Operation(summary = "펀드 매니저 추가 정보 저장", description = "카카오 로그인 후 추가 정보를 db에 저장")
    public void addFundInfo(@RequestBody FundAddInfoRequestDTO fundAddInfoRequestDTO) {
        memberService.saveFundAddInfo(fundAddInfoRequestDTO);
    }

    @GetMapping("/fund/{email}")
    @Operation(summary = "펀드 매니저 개별 조회", description = "펀드 매니저 개별 조회")
    public ResponseEntity<FundEntity> findFundByEmail(@PathVariable(name = "email") String email) {
        Optional<FundEntity> optionalFundManager = fundRepository.findByEmail(email);
        return optionalFundManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{email}")
    @Operation(summary = "유저 개별 조회", description = "유저 개별 조회를 합니다.")
    public ResponseEntity<UserEntity> findUserByEmail(@PathVariable(name = "email") String email) {
        Optional<UserEntity> optionalUserManager = kakaoRepository.findByEmail(email);
        return optionalUserManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/user/investment/update")
    @Operation(summary = "유저 투자성향 업데이트", description = "유저 투자성향을 업데이트 합니다.")
    public void updateUserInvestmentPropensity(@RequestHeader("Authorization") String accessToken, @RequestBody UserEditInvestmentPropensityDTO userEditInvestmentPropensityDTO) {
        long memberId = jwtDecode.executeDecode(accessToken).get("userId", Long.class);
        memberService.updateUserInvestmentPropensity(memberId, userEditInvestmentPropensityDTO);
    }

    @GetMapping("/user/profile")
    @Operation(summary = "유저 마이페이지", description = "유저의 마이페이지 입니다.")
    public ResponseEntity<UserProfileRequestDTO> getUserProfile(@RequestHeader("Authorization") String accessToken) {
        long memberId = jwtDecode.executeDecode(accessToken).get("userId", Long.class);
        UserProfileRequestDTO userProfile = memberService.getUserProfile(memberId);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/fund/profile")
    @Operation(summary = "펀드 마이페이지", description = "펀드의 마이페이지 입니다.")
    public ResponseEntity<FundProfileRequestDTO> getFundProfile(@RequestHeader("Authorization") String accessToken) {
        long memberId = jwtDecode.executeDecode(accessToken).get("fmId", Long.class);
        FundProfileRequestDTO fundProfile = memberService.getFundProfile(memberId);
        return ResponseEntity.ok(fundProfile);
    }

}