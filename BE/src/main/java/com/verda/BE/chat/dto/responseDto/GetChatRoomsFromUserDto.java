package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.repository.ChatRoomInterface;
import java.util.List;
import lombok.Getter;

@Getter
public class GetChatRoomsFromUserDto {
    List<ChatRoomInterface> chatRoomInfoList;

    public GetChatRoomsFromUserDto(List<ChatRoomInterface> chatRoomInfoList){
        this.chatRoomInfoList=chatRoomInfoList;
    }
}
