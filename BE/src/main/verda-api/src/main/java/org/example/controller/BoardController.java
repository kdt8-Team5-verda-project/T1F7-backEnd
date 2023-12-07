package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

//import org.apache.http.impl.bootstrap.HttpServer;
import lombok.RequiredArgsConstructor;
import org.example.oAuth.JwtDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    @Autowired
    private com.verda.BE.board.service.BoardService boardService;
    @Autowired
    private final JwtDecode jwtDecode;

    //    테스트 코드
    @GetMapping("/testCICD")
    public String testCICD() {
        return "TEST";
    }

    //    게시물 작성
    @PostMapping("/board")
    @Operation(summary = "게시물 생성", description = "게시물을 작성합니다.")
    public Long createPost(@RequestBody com.verda.BE.board.dto.requestdto.BoardCreateRequestDTO requestDto, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtDecode.executeDecode(authorizationHeader).get("userId", Long.class);
        return boardService.create(requestDto, userId);
    }


    //    게시물 수정
    @PutMapping("/board/{postId}")
    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
    public Long updatePost(@PathVariable Long postId, @RequestBody com.verda.BE.board.dto.requestdto.BoardUpdateRequestDTO requestDto) {
        return boardService.update(postId, requestDto);
    }


    //    게시물 조회
    //      개별 조회
    @GetMapping("/board/{postId}")
    @Operation(summary = "게시물 개별 조회", description = "게시물을 개별로 조회합니다.")
    public com.verda.BE.board.dto.responsedto.BoardResponseDTO searchById(@PathVariable Long postId) {
        return boardService.searchById(postId);
    }

    //    전체 조회(목록)
    @GetMapping("/board")
    @Operation(summary = "게시물 전체 조회", description = "유저 게시물들을 조회합니다.")

//    public List<BoardListResponseDTO> searchAllDesc() {
//        return boardService.searchAllDesc();
//    }
    public List<com.verda.BE.board.dto.responsedto.BoardListResponseDTO> getPostsLowerThanId(@RequestParam Long lastPostId, @RequestParam int size) {
        return boardService.fetchPostPagesBy(lastPostId, size);
    }

    @GetMapping("/user/board")
    @Operation(summary = "게시물 전체 조회(유저용)" , description = "자신이 작성한 게시글만을 보여줍니다.")
    public Slice<BoardListResponseDTO> getUserPostList(@RequestHeader("Authorization") String authorizationHeader, Pageable pageable){
        Long userId = jwtDecode.executeDecode(authorizationHeader).get("userId", Long.class);
        return boardService.searchByUserId(userId, pageable);
    }

    //    게시물 삭제
    @DeleteMapping("/board/{postId}")
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제합니다.")
    public void deletePost(@PathVariable Long postId) {
        boardService.delete(postId);
    }
}
