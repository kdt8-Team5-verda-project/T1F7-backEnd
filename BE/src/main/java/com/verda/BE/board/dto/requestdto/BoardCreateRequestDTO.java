package com.verda.BE.board.dto.requestdto;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.login.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@NoArgsConstructor
@Getter
public class BoardCreateRequestDTO {
//    private Long postId;
    private String title;
    private String content;
//    private Long userId;
//    private UserEntity user;

    @Builder
    public BoardCreateRequestDTO(Long postId, String title, String content,UserEntity user) {
//        this.postId = postId;
        this.title = title;
        this.content = content;
//        this.user = user;
//        this.userId = userId;
//        this.createdAt = createdAt;
    }

    public UserPostEntity toEntity() {
        return UserPostEntity.builder()
                .title(title)
                .content(content)
//                .createdAt(createdAt)
                .build();
    }

}
