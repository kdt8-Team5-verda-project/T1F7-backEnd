package com.verda.BE.chat.controller;

import com.verda.BE.chat.dto.requestDto.CreateChatRoomRequestDTO;
import com.verda.BE.chat.dto.responseDto.ChatRoomExistDTO;
import com.verda.BE.chat.dto.responseDto.ChatRoomInfoDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsByPostIdFromUserDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromFmDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromUserDTO;
import com.verda.BE.chat.repository.ChatRoomInterface;
import com.verda.BE.chat.service.ChatService;
import com.verda.BE.common.JwtDecode;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatService chatService;
    private final JwtDecode jwtDecode;
    @Operation(summary = "현재 게시글관련 채팅방유무 체크",description = "펀드매니저가 현재접속한 게시글에서 만든 채팅방이 있는지 확인하는 api")
    @GetMapping("/exist/chat/{postId}")
    public ChatRoomExistDTO checkChatExist(@RequestHeader("Authorization") String authorizationHeader, @PathVariable(name = "postId") long postId){
        long fmId = jwtDecode.executeDecode(authorizationHeader).get("fmId", Long.class);
        ChatRoomExistDTO checkExist = chatService.checkExistChat(fmId, postId);
        return checkExist;
    }

    /**
     * 채팅하기 클릭시 채팅방생성함수 실행 채팅방 생성
     */
    @Operation(summary = "채팅방생성", description = "제안서작성 버튼 클릭시 실행")
    @PostMapping("/chat")
    public ChatRoomInfoDTO createChatRoom(@RequestBody CreateChatRoomRequestDTO createChatRoom,@RequestHeader("Authorization") String authorizationHeader) {
        Long fmId = jwtDecode.executeDecode(authorizationHeader).get("fmId", Long.class);
        ChatRoomInfoDTO chatRoomId = chatService.createChatRoom(createChatRoom, fmId);
        return chatRoomId;
    }

    /**
     * 게시물 목록을 먼저 누르면 해당 게시물과 관련된 채팅방목록이 옆에 나옴.
     * 유저 - 채팅방목록 조회
     *
     * @param postId
     * @return
     */
    @Operation(summary = "유저 채팅방목록 조회1", description = "게시물 목록을 먼저 누르면 해당 게시물과 관련된 채팅방목록이 옆에 나옴")
    @GetMapping("/rooms/{postId}")
    public List<GetChatRoomsByPostIdFromUserDTO>  getRoomsListFromUser(@PathVariable(name = "postId") long postId) {
        List<GetChatRoomsByPostIdFromUserDTO> chatListToUser = chatService.getChatListToUser(postId);
        return chatListToUser;
    }

    /**
     * 카카오톡처럼 그냥 쭉 채팅방목록들이 보임
     * 유저용
     * @return
     */
    @Operation(summary = "유저 채팅방목록 조회2", description = "카카오톡처럼 그냥 쭉 채팅방목록들이 보임")
    @GetMapping("/rooms/user")
    public Slice<GetChatRoomsFromUserDTO> getRoomListToUser(@RequestHeader("Authorization") String authorizationHeader, Pageable pageable) {
        Long userId = jwtDecode.executeDecode(authorizationHeader).get("userId", Long.class);
        Slice<GetChatRoomsFromUserDTO> chatListAtRoomView = chatService.getChatListAtRoomView(userId, pageable);
        return chatListAtRoomView;
    }

    /**
     * 카카오톡처럼 그냥 쭉 채팅방목록들이 보임
     * 펀드매니저용
     * @return
     */
    @Operation(summary = "펀드매니저 채팅방목록 조회", description = "카카오톡처럼 그냥 쭉 채팅방목록들이 보임")
    @GetMapping("/rooms/fm")
    public Slice<GetChatRoomsFromFmDTO> getRoomListToFm(@RequestHeader("Authorization") String authorizationHeader, Pageable pageable) {
        long fmId = jwtDecode.executeDecode(authorizationHeader).get("fmId", Long.class);
        Slice<GetChatRoomsFromFmDTO> chatListToFm = chatService.getChatListAtRoomViewFromFm(fmId, pageable);
        return chatListToFm;
    }
}