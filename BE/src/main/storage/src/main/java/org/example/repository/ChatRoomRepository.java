package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.entity.ChatRoomEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    @Query(value = "SELECT C.fm_id, C.post_id, C.user_id, C.room_id, J.content, F.name as target_name"
            + " FROM chat_room C"
            + " LEFT JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message"
            + "                 GROUP BY room_id )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " INNER JOIN fund F"
            + " ON C.fm_id=F.fm_id"
            + " WHERE post_id = :postId"
            , nativeQuery = true)
    List<ChatRoomInterface> getChatListByPostId(@Param("postId") long postId);

    @Query(value = "SELECT C.fm_id, C.post_id, C.user_id, C.room_id, J.content, F.name as target_name"
            + " FROM chat_room C"
            + " LEFT JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message"
            + "                 GROUP BY room_id )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " INNER JOIN fund F"
            + " ON C.fm_id=F.fm_id"
            + " WHERE user_id = :userId"
            , nativeQuery = true)
    Slice<ChatRoomInterface> getChatListByUserId(@Param("userId") long userId, Pageable pageable);

    @Query(value = "SELECT C.fm_id, C.post_id, C.user_id, C.room_id, J.content, U.name as target_name"
            + " FROM chat_room C"
            + " LEFT JOIN (SELECT room_id, content, message_id"
            + "             FROM message"
            + "             WHERE (room_id, message_id)"
            + "             IN ("
            + "                 SELECT room_id, max(message_id)"
            + "                 FROM message "
            + "                 GROUP BY room_id"
            + "                )"
            + "             ORDER BY room_id) J"
            + " ON C.room_id = J.room_id"
            + " INNER JOIN user U"
            + " ON C.user_id=U.user_id"
            + " WHERE fm_id = :fmId"
            , nativeQuery = true)
    Slice<ChatRoomInterface> getChatListByFmId(@Param("fmId") long fmId, Pageable pageable);

    Optional<ChatRoomEntity> findByFundEntityFmIdAndUserPostEntityPostId(Long fmId, Long postId);
}
