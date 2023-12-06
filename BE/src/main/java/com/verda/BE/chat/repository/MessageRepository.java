package com.verda.BE.chat.repository;

import com.verda.BE.chat.entity.MessageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
//    @Query(value ="SELECT * FROM message WHERE chat_room.room_id = :roomId" ,nativeQuery = true)
//    List<MessageEntity> getChatList(@Param("roomId") long roomId);

    @Query(value = "SELECT content,sender_email FROM message where room_id=:roomId",nativeQuery = true)
    List<PreChatInterface> getPreChat(@Param("roomId") long roomId);

    List<MessageEntity> findByChatRoomEntity_Id(long roomId);

    @Query(value = "SELECT * FROM message "
            + "ORDER BY message_id desc limit 1;" ,nativeQuery = true)
    MessageEntity getCurrentMessageEntity();
}
