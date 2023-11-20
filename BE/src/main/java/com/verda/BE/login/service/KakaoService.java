package com.verda.BE.login.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface KakaoService {
    public String getToken(String code) throws Exception;

    ArrayList<Object> getUserInfo(String access_token) throws Exception;
}
