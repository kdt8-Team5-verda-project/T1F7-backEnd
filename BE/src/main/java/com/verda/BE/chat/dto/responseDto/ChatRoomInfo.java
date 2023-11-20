package com.verda.BE.chat.dto.responseDto;

import lombok.Getter;

@Getter
public class ChatRoomInfo {
    private long roomId;
    private String otherName;
    private String recentMessage;
}
