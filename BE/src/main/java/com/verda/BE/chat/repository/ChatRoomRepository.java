package com.verda.BE.chat.repository;

import com.verda.BE.chat.entity.ChatRoomEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query(value = "SELECT fm_id, post_id, user_id, J.room_id, content"
            + " FROM chat_room C"
            + " INNER JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message "
            + "                 GROUP BY room_id"
            + "                )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " WHERE post_id = :postId"
            , nativeQuery = true)
    List<ChatRoomInterface> getChatListBypostId(@Param("postId") long postId);

    @Query(value = "SELECT fm_id, post_id, user_id, J.room_id, content"
            + " FROM chat_room C"
            + " INNER JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message "
            + "                 GROUP BY room_id"
            + "                )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " WHERE user_id = :userId"
            , nativeQuery = true)
    List<ChatRoomInterface> getChatListByUserId(@Param("userId") long userId);

    @Query(value = "SELECT fm_id, post_id, user_id, J.room_id, content"
            + " FROM chat_room C"
            + " INNER JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message "
            + "                 GROUP BY room_id"
            + "                )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " WHERE fm_id = :fmId"
            , nativeQuery = true)
    List<ChatRoomInterface> getChatListByFmId(@Param("fmId") long fmId);
}
