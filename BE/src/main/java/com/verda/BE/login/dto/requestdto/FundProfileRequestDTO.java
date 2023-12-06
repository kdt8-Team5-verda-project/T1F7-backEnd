package com.verda.BE.login.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundProfileRequestDTO {
    private Long fmId;
    private String email;
    private String name;
    private String gender;
    private String age_range;
    private String record;
    private String location;
    private String number;
}
