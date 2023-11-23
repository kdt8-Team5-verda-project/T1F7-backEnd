package com.verda.BE.board.controller;

import com.verda.BE.board.dto.requestdto.BoardCreateRequestDTO;
import com.verda.BE.board.dto.requestdto.BoardUpdateRequestDTO;
import com.verda.BE.board.dto.responsedto.BoardListResponseDTO;
import com.verda.BE.board.dto.responsedto.BoardResponseDTO;
import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
//import org.apache.http.impl.bootstrap.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //    게시물 작성
    @PostMapping("/board/{userId}")
    @Operation(summary = "게시물 생성", description = "게시물을 작성합니다.")
    public Long createPost(@RequestBody BoardCreateRequestDTO requestDto, @PathVariable Long userId) {
        return boardService.create(requestDto, userId);
    }
//    public Long createPost(HttpServletRequest request){
//        Long userId = (Long) request.getAttribute("userId");
//        UserEntity userEntity = boardService.findByuserId(userId);
//        return userEntity;
//    }

    //    게시물 수정
    @PutMapping("/board/{postId}")
    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
    public Long updatePost(@PathVariable Long postId, @RequestBody BoardUpdateRequestDTO requestDto) {
        return boardService.update(postId, requestDto);
    }

    //    게시물 조회
    //      개별 조회
    @GetMapping("/board/{postId}")
    @Operation(summary = "게시물 개별 조회", description = "게시물을 개별로 조회합니다.")
    public BoardResponseDTO searchById(@PathVariable Long postId) {
        return boardService.searchById(postId);
    }

    //    전체 조회(목록)
    @GetMapping("/board")
    @Operation(summary = "게시물 전체 조회", description = "게시물들을 조회합니다.")

//    public List<BoardListResponseDTO> searchAllDesc() {
//        return boardService.searchAllDesc();
//    }
    public List<BoardListResponseDTO> getPostsLowerThanId(@RequestParam Long lastPostId,@RequestParam int size){
        return boardService.fetchPostPagesBy(lastPostId,size);
    }

    //    게시물 삭제
    @DeleteMapping("/board/{postId}")
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제합니다.")
    public void deletePost(@PathVariable Long postId) {
        boardService.delete(postId);
    }
}
