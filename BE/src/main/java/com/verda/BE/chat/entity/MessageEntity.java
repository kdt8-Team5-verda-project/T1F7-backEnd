package com.verda.BE.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    private ChatRoomEntity chatRoomEntity;

    @PrePersist
    void prePersist() {
        this.sendTime = LocalDateTime.now();
    }


}
