package com.verda.BE.login.member.domain;

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
    private String age_range;
    private String file;
    private String record;
    private String location;
    private String number;

    @Builder
    public FundEntity(long fmId, String email, String name, String age_range, String file, String record, String location,
                      String number) {
        this.fmId = fmId;
        this.email = email;
        this.name = name;
        this.age_range = age_range;
        this.file = file;
        this.record = record;
        this.location = location;
        this.number = number;
    }
}