package org.example.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

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
    private final FundRepository fundRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String dir = "/FundPic";
    private String defaultUrl = "https://verda-s3.s3.ap-northeast-2.amazonaws.com";

public String uploadFile(MultipartFile file, String email) throws IOException {
    // FundEntity를 email을 기반으로 찾습니다.
    FundEntity fundEntity = fundRepository.findByEmail(email)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL));

    // 파일을 S3에 업로드하고 URL을 받아옵니다.
    String fileUrl = uploadFileToS3(file);

    // FundFileEntity를 생성하여 저장합니다.
    FundPicEntity fundFileEntity = FundPicEntity.builder()
            .fundEntity(fundEntity)
            .fileUrl(fileUrl)
            .build();

    fundPicRepository.save(fundFileEntity);

    fundEntity.setFileId(fundFileEntity.getFileId());
    fundRepository.save(fundEntity);


    return fileUrl;
}

    private String uploadFileToS3(MultipartFile file) throws IOException {
        String bucketDir = bucketName + dir;
        String dirUrl = defaultUrl + dir + "/";
        String fileName = generateFileName(file);

        amazonS3Client.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));

        return dirUrl + fileName;
    }

    public String getFundPic(Long fmId) {
        System.out.println("1" + fmId);
        FundPicEntity fundPicEntity = fundPicRepository.findByFundEntityFmId(fmId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FILE));
        System.out.println("2" + fundPicEntity);


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