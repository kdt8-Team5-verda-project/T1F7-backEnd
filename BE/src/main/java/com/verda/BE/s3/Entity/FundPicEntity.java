package com.verda.BE.s3.Entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "FundPic")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundPicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String fileName;
    private String fileUUID;
    private String fileUrl;
}