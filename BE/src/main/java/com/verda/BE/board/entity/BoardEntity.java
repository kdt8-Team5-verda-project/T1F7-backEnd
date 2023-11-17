package com.verda.BE.board.entity;


import com.verda.BE.login.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "userPost")
public final class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    UserEntity userEntity;


    @Builder
    public BoardEntity(Long postId, String title, String content, Timestamp createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

}
