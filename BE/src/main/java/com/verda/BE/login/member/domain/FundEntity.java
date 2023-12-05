package com.verda.BE.login.member.domain;

import com.verda.BE.s3.Entity.FundPicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Fund")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @OneToMany(mappedBy = "ChatRoomEntity")
    private long fmId;
    private String email;
    private String name;
    private String gender;
    private String age_range;
    private String record;
    private String location;
    private String number;
    private Long fileId;

    @OneToOne(mappedBy = "fundEntity") // 양방향 매핑
    @JoinColumn(name = "fmId")

    private FundPicEntity fundPicEntity;

    @Builder
    public FundEntity(long fmId, String email, String name, String gender, String age_range, String record, String location,
                      String number) {
        this.fmId = fmId;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.age_range = age_range;
        this.record = record;
        this.location = location;
        this.number = number;
    }
}