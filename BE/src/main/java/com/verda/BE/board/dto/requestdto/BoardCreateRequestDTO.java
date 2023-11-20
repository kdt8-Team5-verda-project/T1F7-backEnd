package com.verda.BE.board.dto.requestdto;

import com.verda.BE.board.entity.UserPostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class BoardCreateRequestDTO {
    private Long postId;
    private String title;
    private String content;
    private Timestamp createdAt;

    @Builder
    public BoardCreateRequestDTO(Long postId, String title, String content, Timestamp createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UserPostEntity toEntity(){
        return UserPostEntity.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

}
