package com.verda.BE.chat.dto.responseDto;

import java.time.LocalDateTime;

public class RecieveMessageResponseDTO {
    private long roomId;
    private String content;
    private String sender;
    private LocalDateTime sendTime;
}
