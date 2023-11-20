package com.verda.BE.chat.entity;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.login.entity.FundEntity;
import com.verda.BE.login.entity.UserEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatRoom")
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "chatRoomEntity")
    private List<MessageEntity> messages = new ArrayList<>();

    public ChatRoomEntity(UserPostEntity userPostEntity, UserEntity userEntity, FundEntity fundEntity){
        this.userEntity=userEntity;
        this.fundEntity=fundEntity;
        this.userPostEntity=userPostEntity;
    }
}
