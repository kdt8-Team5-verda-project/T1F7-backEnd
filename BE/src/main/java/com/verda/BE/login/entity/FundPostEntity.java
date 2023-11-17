package com.verda.BE.login.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FundPost")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FundPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @OneToMany(mappedBy = "ChatRoomEntity")
    private int fmId;
    private String email;
    private String name;
    private String age;
    private String file;
    private String record;
    private String location;
    private String number;

    @Builder
    public FundPostEntity(int fmId, String email, String name, String age, String file, String record, String location,
                          String number) {
        this.fmId = fmId;
        this.email = email;
        this.name = name;
        this.age = age;
        this.file = file;
        this.record = record;
        this.location = location;
        this.number = number;
    }
}
