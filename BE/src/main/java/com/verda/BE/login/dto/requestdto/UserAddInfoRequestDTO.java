package com.verda.BE.login.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddInfoRequestDTO {
    private String investmentPropensity;
    private String number;
    private String email;
}
