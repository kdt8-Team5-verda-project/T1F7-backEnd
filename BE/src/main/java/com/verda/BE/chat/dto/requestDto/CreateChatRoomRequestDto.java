package com.verda.BE.chat.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomRequestDto {
    private long postId;
    private long userId;
    private long fmId;
}
