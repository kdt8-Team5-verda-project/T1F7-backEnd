package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.service.S3Service;
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
    @GetMapping("/{fmId}")
    @Operation(summary = "펀드 매니저 파일 찾기", description = "해당하는 펀드 매니저의 파일을 찾습니다.")
    public ResponseEntity<String> getFundPic(@PathVariable(name = "fmId") Long fmId) {
        System.out.println("3");
        String fileUrl = s3Service.getFundPic(fmId);
        return ok(fileUrl);
    }
}