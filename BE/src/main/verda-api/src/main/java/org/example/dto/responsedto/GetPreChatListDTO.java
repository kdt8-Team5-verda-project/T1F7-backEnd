package org.example.dto.responsedto;

import com.verda.BE.chat.entity.MessageEntity;
import com.verda.BE.chat.repository.PreChatInterface;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPreChatListDTO {
    List<PreChatInterface> preChatList;
    public GetPreChatListDTO(List<PreChatInterface> preChatList) {
        this.preChatList = preChatList;
    }
}
