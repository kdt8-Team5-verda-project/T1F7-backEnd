package com.verda.BE.chat.controller;

import com.verda.BE.chat.dto.requestDto.CreateChatRoomRequestDto;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromFmDto;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromUserDto;
import com.verda.BE.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatService chatService;

    /**
     * 채팅하기 클릭시 채팅방생성함수 실행
     * 채팅방 생성
     */
    @PostMapping("/chat")
    public void createChatRoom(@RequestBody CreateChatRoomRequestDto createChatRoom) {
        chatService.createChatRoom(createChatRoom);
    }

    /**
     * TODO-jwt 생성시 pathVariable -> 헤더로 정보받는걸로 변경
     * 게시물 목록을 먼저 누르면 해당 게시물과 관련된 채팅방목록이 옆에 나옴.
     * 유저 - 채팅방목록 조회
     * @param postId
     * @return
     */
    @GetMapping("/rooms/{postId}")
    public GetChatRoomsFromUserDto getRoomsListFromUser(@PathVariable(name = "postId") long postId){
        GetChatRoomsFromUserDto chatListToUser = chatService.getChatListToUser(postId);
        return chatListToUser;
    }

    /**
     * TODO-jwt 생성시 pathVariable -> 헤더로 정보받는걸로 변경
     * 카카오톡처럼 그냥 쭉 채팅방목록들이 보임
     * 유저용
     * @param userId
     * @return
     */
    @GetMapping("/rooms/user/{userId}")
    public GetChatRoomsFromUserDto getRoomListToUser(@PathVariable(name = "userId") long userId){
        GetChatRoomsFromUserDto chatListAtRoomView = chatService.getChatListAtRoomView(userId);
        return chatListAtRoomView;
    }

    /**
     * TODO-jwt 생성시 pathVariable -> 헤더로 정보받는걸로 변경
     * 카카오톡처럼 그냥 쭉 채팅방목록들이 보임
     * 펀드매니저용
     * @param fmId
     * @return
     */
    @GetMapping("/rooms/fm/{fmId}")
    public GetChatRoomsFromFmDto getRoomListToFm(@PathVariable(name="fmId")long fmId){
        GetChatRoomsFromFmDto chatListToFm = chatService.getChatListAtRoomViewFromFm(fmId);
        return chatListToFm;
    }



}