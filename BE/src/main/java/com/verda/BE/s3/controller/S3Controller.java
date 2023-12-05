package com.verda.BE.s3.controller;


import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.s3.Entity.FundPicEntity;
import com.verda.BE.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/fundpic")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/uploadfundpic")
    @Operation(summary = "펀드 매니저 파일 업로드", description = "펀드 매니저 파일 업로드 합니다.")
    public ResponseEntity<String> uploadFile(@RequestParam String email, @RequestBody MultipartFile file) throws IOException {
        String url = s3Service.uploadFile(file, email);
        return ok(url);
    }
    @GetMapping("/{fmid}")
    @Operation(summary = "펀드 매니저 파일 찾기", description = "해당하는 펀드 매니저의 파일을 찾습니다.")
    public ResponseEntity<String> getFundPic(@PathVariable Long fmId) {
        String fileUrl = s3Service.getFundPic(fmId);
        return ResponseEntity.ok(fileUrl);
    }
}