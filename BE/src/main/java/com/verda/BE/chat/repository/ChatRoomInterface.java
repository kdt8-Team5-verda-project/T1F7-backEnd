package com.verda.BE.chat.repository;

public interface ChatRoomInterface {
    Long getPost_id();
    Long getFm_id();
    Long getUser_id();
    Long getRoom_id();
    String getContent();
}
