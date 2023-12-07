package org.example.dto.requestdto;

import com.verda.BE.login.domain.AuthTokens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDTO {
    private boolean isExistUser;
    private AuthTokens authToken;
    private String email;
}
