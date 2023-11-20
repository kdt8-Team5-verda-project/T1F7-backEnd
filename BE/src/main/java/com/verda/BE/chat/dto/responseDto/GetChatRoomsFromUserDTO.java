package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.repository.ChatRoomInterface;
import java.util.List;
import lombok.Getter;

@Getter
public class GetChatRoomsFromUserDTO {
    List<ChatRoomInterface> chatRoomInfoList;

    public GetChatRoomsFromUserDTO(List<ChatRoomInterface> chatRoomInfoList){
        this.chatRoomInfoList=chatRoomInfoList;
    }
}
