package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
    private final AuthTokensGenerator authTokensGenerator;

//    public String extractAccessToken(String authorizationHeader) {
//        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
//            String accessToken = authorizationHeader.substring(7);
//            System.out.println("Extracted Access Token: " + accessToken);
//            return accessToken;
//        }
//        System.out.println("Invalid or Missing Authorization Header");
//        // 올바르지 않거나 누락된 Authorization 헤더를 처리
//        throw new ApiException(ErrorCode.NOT_FOUND_TOKEN);
//    }

    public Long extractMemberId(String authorization) {
        try {
            System.out.println("1");
            String token = extractAccessToken(authorization);
            return authTokensGenerator.extractMemberId(token);
        } catch (ApiException e) {
            throw new ApiException(ErrorCode.NOT_FOUND_TOKEN);
        }
    }

    private String extractAccessToken(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new ApiException(ErrorCode.NOT_FOUND_TOKEN);
    }

    public void saveUserAddInfo(UserAddInfoRequestDTO requestDTO) {
        UserEntity user = kakaoRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));
        user.setInvestmentPropensity(requestDTO.getInvestmentPropensity());
        user.setNumber(requestDTO.getNumber());
        kakaoRepository.save(user);
    }

    public void saveFundAddInfo(FundAddInfoRequestDTO requestDTO) {
        FundEntity fund = fundRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));
        fund.setRecord(requestDTO.getRecord());
        fund.setLocation(requestDTO.getLocation());
        fund.setNumber(requestDTO.getNumber());

        fundRepository.save(fund);
    }

    public boolean isExistUserAddInfo(String email) {
        UserEntity user = kakaoRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL));

        if (user.getNumber() == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isExistFundAddInfo(String email) {
        FundEntity fund = fundRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL));


        if (fund.getNumber() == null) {
            return true;
        } else {
            return false;
        }
    }
    public void updateUserInvestmentPropensity(Long memberId, UserEditInvestmentPropensityDTO userEditInvestmentPropensityDTO) {
        UserEntity user = kakaoRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));
        user.setInvestmentPropensity(userEditInvestmentPropensityDTO.getInvestmentPropensity());
        kakaoRepository.save(user);
    }

    public UserProfileRequestDTO getUserProfile(Long userId) {
        UserEntity userEntity = kakaoRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));

        UserProfileRequestDTO user = new UserProfileRequestDTO();
        user.setName(userEntity.getName());
        user.setNumber(userEntity.getNumber());
        user.setGender(userEntity.getGender());
        user.setBirthday(userEntity.getBirthday());
        user.setEmail(userEntity.getEmail());
        user.setAgeRange(userEntity.getAge_range());
        return user;
    }

    public FundProfileRequestDTO getFundProfile(Long fmId) {
        FundEntity fundEntity = fundRepository.findById(fmId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));

        FundProfileRequestDTO fund = new FundProfileRequestDTO();
        fund.setFmId((fundEntity.getFmId()));
        fund.setName(fundEntity.getName());
        fund.setNumber(fundEntity.getNumber());
        fund.setGender(fundEntity.getGender());
        fund.setRecord(fundEntity.getRecord());
        fund.setEmail(fundEntity.getEmail());
        fund.setAge_range(fundEntity.getAge_range());
        fund.setLocation(fundEntity.getLocation());
        return fund;
    }
}