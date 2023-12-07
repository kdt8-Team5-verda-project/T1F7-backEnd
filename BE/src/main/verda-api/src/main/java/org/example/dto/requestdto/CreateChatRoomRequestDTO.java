package org.example.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomRequestDTO {
    private long postId;
    private long userId;
    private long fmId;
}
