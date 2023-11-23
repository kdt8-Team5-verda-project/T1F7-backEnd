package com.verda.BE.chat.entity;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.UserEntity;
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
    UserPostEntity userPostEntity;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId")
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "fmId", referencedColumnName = "fmId")
    FundEntity fundEntity;

    public ChatRoomEntity(UserPostEntity userPostEntity, UserEntity userEntity, FundEntity fundEntity){
        this.userEntity=userEntity;
        this.fundEntity=fundEntity;
        this.userPostEntity=userPostEntity;
    }
}
