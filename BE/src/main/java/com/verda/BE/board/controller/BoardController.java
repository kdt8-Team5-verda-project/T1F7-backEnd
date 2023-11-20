package com.verda.BE.board.controller;


import com.verda.BE.board.dto.requestdto.BoardCreateRequestDTO;
import com.verda.BE.board.dto.requestdto.BoardUpdateRequestDTO;
import com.verda.BE.board.dto.responsedto.BoardListResponseDTO;
import com.verda.BE.board.dto.responsedto.BoardResponseDTO;
import com.verda.BE.board.service.BoardService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

//
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK !!"),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
//    })


//    @PostMapping("/board/write")
//    @Operation(summary = "게시물 생성", description = "게시물을 작성합니다.")
//    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardRequestDTO requestDTO) {
//        try {
//            // 게시물을 작성하고 작성된 게시물을 반환
//            UserEntity user = getCurrentUser();
//            // 현재 사용자 정보를 가져오는 메서드 (예: 세션 또는 인증 정보 활용)
//            BoardResponseDTO responseDTO = boardService.createBoard(requestDTO, user);
//
//            return ResponseEntity.ok(responseDTO);
//        } catch (Exception e) {
//            // 예외 처리, 로깅 및 적절한 응답 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    // 현재 사용자 정보를 가져오는 메서드 (예: 세션 또는 인증 정보 활용)
//    private UserEntity getCurrentUser() {
//        // 실제로는 세션 또는 인증 정보를 활용하여 현재 사용자 정보를 가져오는 로직을 구현해야 합니다.
//        // 이 예제에서는 가상의 사용자 정보를 반환하도록 되어 있습니다.
//        return UserEntity.builder()
//                .userId(1)
//                .email("user@example.com")
//                .name("John Doe")
//                .build();
//    }

//    @PostMapping("/board")
//    @ResponseBody
//    public void postBoard(@RequestBody BoardRequestDTO requestDTO) {
//        boardService.createPost(requestDTO);
//    }

    //    게시물 작성
    @PostMapping("/board")
//    왜 이거 넣으면 오류가??...
//    @Operation(summary = "게시물 생성", description = "게시물을 작성합니다.")
    public Long createPost(@RequestBody BoardCreateRequestDTO requestDto) {
        return boardService.create(requestDto);
    }

    //    게시물 수정
    @PutMapping("/board/{postId}")
    public Long updatePost(@PathVariable Long postId, @RequestBody BoardUpdateRequestDTO requestDto) {
        return boardService.update(postId, requestDto);
    }

    //    게시물 조회
    //      개별 조회
    @GetMapping("/board/{postId}")
    public BoardResponseDTO searchById(@PathVariable Long postId) {
        return boardService.searchById(postId);
    }

    //    전체 조회(목록)
    @GetMapping("/board")
    public List<BoardListResponseDTO> searchAllDesc() {
        return boardService.searchAllDesc();
    }

    //    게시물 삭제
    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable Long postId) {
        boardService.delete(postId);
    }
}
