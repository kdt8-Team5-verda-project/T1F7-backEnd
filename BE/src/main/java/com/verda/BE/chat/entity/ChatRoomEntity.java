package com.verda.BE.chat.entity;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.login.entity.FundEntity;
import com.verda.BE.login.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatRoom")
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private long id;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    UserPostEntity userPostEntity;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "fmId", referencedColumnName = "fmId")
    FundEntity fundEntity;

    public ChatRoomEntity(UserPostEntity userPostEntity, UserEntity userEntity, FundEntity fundEntity) {
        this.userPostEntity = userPostEntity;
        this.fundEntity = fundEntity;
        this.userEntity = userEntity;
    }
}
