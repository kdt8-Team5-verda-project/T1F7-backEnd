package com.verda.BE.chat.dto.responseDto;

import java.time.LocalDateTime;

public class RecieveMessageResponseDTO {
    private String content;
    private String sender;
    private LocalDateTime sendTime;

    public RecieveMessageResponseDTO(String content, String sender, LocalDateTime sendTime){
        this.content=content;
        this.sender=sender;
        this.sendTime=sendTime;
    }
}
