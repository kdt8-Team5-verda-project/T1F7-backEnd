package com.verda.BE.login.controller;


import com.verda.BE.login.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class KakaoController {
    @GetMapping("kakaoTerms")
    public String KakaoConnect() {
        //StringBuffer : 가변한 문자열을 처리하기 위한 클래스. 새로운 문자열을 추가하거나 변경하게 되면 기존의 객체로 추가 및 변경 됨.
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "760561f260038af80dbfd10be980014f");
        url.append("&redirect_uri=https://localhost:8080/login");
        url.append("&response_type=code");

        return "redirect:" + url.toString();
    }




    @Autowired
    private KakaoService kakaoService ;

    @RequestMapping(value = "/kakao")
    public String kakaoLogin(@RequestParam("code") String code, Model model , HttpSession session) throws Exception {

        //code로 토큰 받음
        String access_token = kakaoService.getToken(code);

        //토큰으로 사용자 정보 담은 list 가져오기
        ArrayList<Object> list = kakaoService.getUserInfo(access_token);

        //list 모델에 담아 view로 넘김
        model.addAttribute("list", list);

        return "userInfo";
    }

}
