package com.verda.BE.chat.dto.responseDto;

import com.verda.BE.chat.entity.MessageEntity;
import java.util.List;
import lombok.Getter;

@Getter
public class GetPreChatListDTO {
    List<MessageEntity> preChatList;
    public GetPreChatListDTO(List<MessageEntity> preChatList) {
        this.preChatList = preChatList;
    }
}
