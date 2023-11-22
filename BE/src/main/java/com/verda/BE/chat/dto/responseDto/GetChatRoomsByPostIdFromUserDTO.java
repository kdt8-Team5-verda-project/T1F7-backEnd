package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.repository.ChatRoomInterface;
import java.util.List;
import lombok.Getter;

@Getter
public class GetChatRoomsByPostIdFromUserDTO {
    List<ChatRoomInterface> chatList;

    public GetChatRoomsByPostIdFromUserDTO(List<ChatRoomInterface> chatList) {
        this.chatList=chatList;
    }
}
