package com.verda.BE.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    private Long postId;
    private String title;
    private String content;
    private Long createdAt;



}
