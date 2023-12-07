package com.verda.BE.board.dto.responsedto;

import com.verda.BE.board.entity.UserPostEntity;
import lombok.Getter;

@Getter
public class BoardListResponseDTO {
    private Long postId;
    private String title;

    private String name;


    public BoardListResponseDTO(UserPostEntity entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.name = (entity.getUserEntity() != null) ? entity.getUserEntity().getName() : null;
    }
}
