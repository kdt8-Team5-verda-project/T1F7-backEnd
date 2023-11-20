package com.verda.BE.chat.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequestDTO {
    private long roomId;
    private String content;
    private String sender;
}
