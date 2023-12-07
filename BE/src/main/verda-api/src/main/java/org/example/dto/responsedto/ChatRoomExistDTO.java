package org.example.dto.responsedto;

import lombok.Getter;

@Getter
public class ChatRoomExistDTO {
    private long roomId;

    public ChatRoomExistDTO(long roomId){
        this.roomId=roomId;
    }
}
