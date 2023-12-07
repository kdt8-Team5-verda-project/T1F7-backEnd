package org.example.dto.responsedto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RecieveMessageResponseDTO {
    private String content;
    private String sender;
    private LocalDateTime sendTime;

    public RecieveMessageResponseDTO(String content, String sender, LocalDateTime sendTime){
        this.content=content;
        this.sender=sender;
        this.sendTime=sendTime;
    }
}
