package com.verda.BE.chat.entity;

import com.verda.BE.board.entity.BoardEntity;
import com.verda.BE.login.entity.FundEntity;
import com.verda.BE.login.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chatRoom")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private long id;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    BoardEntity boardEntity;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId")
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "fmId", referencedColumnName = "fmId")
    FundEntity fundEntity;
}
