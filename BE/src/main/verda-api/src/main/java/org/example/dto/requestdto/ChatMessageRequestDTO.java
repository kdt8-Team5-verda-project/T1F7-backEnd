package org.example.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDTO {
    private long roomId;
    private String content;
    private String sender_email;
}
