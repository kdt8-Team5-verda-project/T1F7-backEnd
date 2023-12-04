package com.verda.BE.s3.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import com.verda.BE.common.ErrorCode;
import com.verda.BE.exception.ApiException;
import com.verda.BE.s3.Entity.FundPicEntity;
import com.verda.BE.s3.repository.FundPicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final FundPicRepository fundPicRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String dir = "/FundPic";
    private String defaultUrl = "https://verda-s3.s3.ap-northeast-2.amazonaws.com";

    public String uploadFile(MultipartFile file) throws IOException {

        String bucketDir = bucketName + dir;
        String dirUrl = defaultUrl + dir + "/";
        String fileName = generateFileName(file);

        amazonS3Client.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));

        String fileUUID = fileName.split("-")[0];
        String originalName = fileName.split("-")[1];

        FundPicEntity fundPicEntity = FundPicEntity.builder()
                .fileName(originalName)
                .fileUUID(fileUUID)
                .fileUrl(dirUrl + fileName)
                .build();

        fundPicRepository.save(fundPicEntity);

        return dirUrl + fileName;

    }
    public String getFundPic(Long fileId) {
        FundPicEntity fundPicEntity = fundPicRepository.findByFileId(fileId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FILE));

        return fundPicEntity.getFileUrl();
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }
}