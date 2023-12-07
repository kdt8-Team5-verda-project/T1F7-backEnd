package com.verda.BE.board.dto.requestdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardUpdateRequestDTO {
    private String title;
    private String content;

    @Builder
    public BoardUpdateRequestDTO(String title,String content){
        this.title = title;
        this.content = content;

    }

}
