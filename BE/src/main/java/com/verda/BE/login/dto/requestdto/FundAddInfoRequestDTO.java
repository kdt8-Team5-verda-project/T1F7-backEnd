package com.verda.BE.login.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundAddInfoRequestDTO {
    private String number;
    private String record;
    private String location;
    private String email;
}