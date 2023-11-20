package com.verda.BE.chat.repository;

public interface ChatRoomInterface {
    Long getPostId();
    Long getFmId();
    Long getUserId();
    Long getRoomId();
    String getContent();
}
