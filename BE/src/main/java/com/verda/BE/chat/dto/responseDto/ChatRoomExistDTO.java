package com.verda.BE.chat.dto.responseDto;

import lombok.Getter;

@Getter
public class ChatRoomExistDTO {
    private long roomId;

    public ChatRoomExistDTO(long roomId){
        this.roomId=roomId;
    }
}
