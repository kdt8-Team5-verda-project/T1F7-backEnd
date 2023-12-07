package org.example.dto.responsedto;

import lombok.Getter;

@Getter
public class ChatRoomInfoDTO {
    private long roomId;

    public ChatRoomInfoDTO(long roomId){
        this.roomId=roomId;
    }
}
