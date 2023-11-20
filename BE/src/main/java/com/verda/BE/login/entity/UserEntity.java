package com.verda.BE.login.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String email;
    private String name;
    private String age;
    private String gender;
    private String location;
    private String investmentPropensity;
    private String number;
    //test

    @Builder
    public UserEntity(long userId, String email, String name, String age, String gender, String location,
                      String investmentPropensity, String number) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.investmentPropensity = investmentPropensity;
        this.number = number;

    }
}
