package com.verda.BE.login.member.application;

import com.verda.BE.login.domain.AuthTokensGenerator;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.UserEntity;
import com.verda.BE.login.member.domain.KakaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
    private final AuthTokensGenerator authTokensGenerator;

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

    @GetMapping("/user/{accessToken}")
    @Operation(summary = "특정 유저 조회", description = "특정 유저를 조회함.")
    public ResponseEntity<UserEntity> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseEntity.ok(kakaoRepository.findById(memberId).get());
    }

    @PostMapping("/user/{email}/addinfo")
    @Operation(summary = "유저 추가 정보 저장", description = "카카오 로그인 후 추가 정보를 db에 저장")

//    public ResponseEntity<UserEntity> AddInfo(
//            @PathVariable String accessToken,
//            @RequestParam String investmentPropensity,
//            @RequestParam String number) {
//
//        Long memberId = authTokensGenerator.extractMemberId(accessToken);
//
//        return kakaoRepository.findById(memberId).map(userEntity -> {
//            userEntity.setInvestmentPropensity(investmentPropensity);
//            userEntity.setNumber(number);
//            UserEntity savedUser = kakaoRepository.save(userEntity);
//
//            return ResponseEntity.ok(savedUser);
//        }).orElse(ResponseEntity.notFound().build());
//    }
    public ResponseEntity<UserEntity> AddUserInfo(
            @PathVariable String email,
            @RequestParam String investmentPropensity,
            @RequestParam String number) {

        // 이메일을 기반으로 회원을 찾음
        Optional<UserEntity> optionalUser = kakaoRepository.findByEmail(email);

        // 회원이 존재하면 추가 정보를 업데이트하고 저장
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            userEntity.setInvestmentPropensity(investmentPropensity);
            userEntity.setNumber(number);
            UserEntity savedUser = kakaoRepository.save(userEntity);
            return ResponseEntity.ok(savedUser);
        } else {
            // 회원이 존재하지 않으면 404 응답 반환
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/fund/{email}/addinfo")
    @Operation(summary = "펀드 매니저 추가 정보 저장", description = "카카오 로그인 후 추가 정보를 db에 저장")
    public ResponseEntity<FundEntity> addFundInfo(
            @PathVariable String email,
            @RequestParam String file,
            @RequestParam String record,
            @RequestParam String location,
            @RequestParam String number) {

        try {
            // 이메일을 기반으로 펀드 매니저를 찾음
            Optional<FundEntity> optionalFund = fundRepository.findByEmail(email);

            // 펀드 매니저가 존재하면 추가 정보를 업데이트하고 저장
            if (optionalFund.isPresent()) {
                FundEntity fundEntity = optionalFund.get();
                fundEntity.setFile(file);
                fundEntity.setRecord(record);
                fundEntity.setLocation(location);
                fundEntity.setNumber(number);
                FundEntity savedFund = fundRepository.save(fundEntity);
                return ResponseEntity.ok(savedFund);
            } else {
                // 펀드 매니저가 존재하지 않으면 404 응답 반환
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 예외 발생 시 로그에 기록
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fund/{email}")
    @Operation(summary = "펀드 매니저 조회", description = "펀드 매니저 개별 조회")
    public ResponseEntity<FundEntity> findFundByEmail(@PathVariable String email) {
        Optional<FundEntity> optionalFundManager = fundRepository.findByEmail(email);
        return optionalFundManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}