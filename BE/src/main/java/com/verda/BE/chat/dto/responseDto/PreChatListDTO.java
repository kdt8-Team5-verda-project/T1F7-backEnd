package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.entity.MessageEntity;
import lombok.Getter;

@Getter
public class PreChatListDTO {
    private String content;
    private String sender_email;

    public PreChatListDTO(MessageEntity message){
        this.content= message.getContent();
        this.sender_email= message.getSenderEmail();
    }
}
