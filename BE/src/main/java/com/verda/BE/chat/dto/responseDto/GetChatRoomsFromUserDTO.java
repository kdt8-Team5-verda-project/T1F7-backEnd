package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.repository.ChatRoomInterface;
import java.util.List;
import lombok.Getter;

@Getter
public class GetChatRoomsFromUserDTO {
    private Long postId;
    private Long fmId;
    private Long userId;
    private Long roomId;
    private String content;
    private String tagetName;

    public GetChatRoomsFromUserDTO(ChatRoomInterface chatRoomInterface){
        this.postId=chatRoomInterface.getRoom_id();
        this.fmId=chatRoomInterface.getFm_id();
        this.userId=chatRoomInterface.getUser_id();
        this.roomId=chatRoomInterface.getRoom_id();
        this.content=chatRoomInterface.getContent();
        this.tagetName= chatRoomInterface.getTarget_Name();
    }
}
