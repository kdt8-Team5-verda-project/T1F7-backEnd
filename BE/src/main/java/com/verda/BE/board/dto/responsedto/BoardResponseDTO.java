package com.verda.BE.board.dto.responsedto;

import com.verda.BE.board.entity.BoardEntity;
import lombok.Getter;

@Getter
public class BoardResponseDTO {
    private Long postId;
    private String title;
    private String content;
    private Long createdAt;

    public BoardResponseDTO(BoardEntity entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().getTime();

    }

}
