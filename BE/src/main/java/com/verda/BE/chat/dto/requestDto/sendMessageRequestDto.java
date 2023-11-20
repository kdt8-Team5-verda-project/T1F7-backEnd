package com.verda.BE.chat.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sendMessageRequestDto {
    private long roomId;
    private String content;
}
