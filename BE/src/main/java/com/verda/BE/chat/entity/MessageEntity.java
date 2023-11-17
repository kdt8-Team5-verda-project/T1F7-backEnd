package com.verda.BE.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
