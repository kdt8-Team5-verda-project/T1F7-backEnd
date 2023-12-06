package com.verda.BE.board.dto.responsedto;

import com.verda.BE.board.entity.UserPostEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {
    private Long postId;
    private String title;
    private String content;

    private LocalDateTime createdAt;
    private String name;
    private Long userId;


    public BoardResponseDTO(UserPostEntity entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt=entity.getCreatedAt();
        this.name = (entity.getUserEntity() != null) ? entity.getUserEntity().getName() : null;
        this.userId = (entity.getUserEntity()!=null) ? entity.getUserEntity().getUserId() : null;

    }

}
