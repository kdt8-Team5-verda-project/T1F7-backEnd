package com.verda.BE.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDTO {
    private Long postId;
    private String title;
    private String content;
    private Long createdAt;

}
