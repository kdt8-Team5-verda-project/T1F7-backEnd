package com.verda.BE.board.dto.requestdto;

import com.verda.BE.board.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class BoardRequestDTO {
    private Long postId;
    private String title;
    private String content;
    private Timestamp createdAt;

    @Builder
    public BoardRequestDTO(Long postId, String title, String content, Timestamp createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

}
