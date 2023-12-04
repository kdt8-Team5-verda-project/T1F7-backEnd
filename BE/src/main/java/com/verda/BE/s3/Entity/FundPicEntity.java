package com.verda.BE.s3.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FundPicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String fileName;
    private String fileUUID;
    private String fileUrl;

    public FundPicEntity(Long fileId, String fileName, String fileUUID,String fileUrl) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileUUID = fileUUID;
        this.fileUrl = fileUrl;
    }
}