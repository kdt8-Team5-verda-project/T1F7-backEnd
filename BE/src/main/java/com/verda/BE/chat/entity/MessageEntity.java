package com.verda.BE.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import org.springframework.messaging.Message;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column
    private LocalDateTime sendTime;

    @Column
    private String senderEmail;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoomEntity chatRoomEntity;

    @PrePersist
    void prePersist() {
        this.sendTime = LocalDateTime.now();
    }

    public MessageEntity(String content, String senderEmail, ChatRoomEntity chatRoomEntity){
        this.chatRoomEntity=chatRoomEntity;
        this.content=content;
        this.senderEmail=senderEmail;
    }


}
