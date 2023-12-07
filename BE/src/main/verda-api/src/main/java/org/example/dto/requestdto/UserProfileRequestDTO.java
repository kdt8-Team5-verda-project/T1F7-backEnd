package org.example.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {
    private String email;
    private String birthday;
    private String gender;
    private String name;
    private String number;
    private String ageRange;
}
