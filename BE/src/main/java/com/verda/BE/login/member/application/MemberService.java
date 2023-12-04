package com.verda.BE.login.member.application;


import com.verda.BE.common.ErrorCode;
import com.verda.BE.exception.ApiException;
import com.verda.BE.login.dto.requestdto.FundAddInfoRequestDTO;
import com.verda.BE.login.dto.requestdto.UserAddInfoRequestDTO;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.KakaoRepository;
import com.verda.BE.login.member.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;

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
        fund.setFile(requestDTO.getFile());
        fund.setNumber(requestDTO.getNumber());

        fundRepository.save(fund);
    }

    public boolean isExistUserAddInfo(String email) {
        UserEntity user = kakaoRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL));

        if (user.getEmail() == null || user.getNumber() == null) {
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
}