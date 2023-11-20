package com.verda.BE.chat.controller;

import com.verda.BE.chat.dto.requestDto.SendMessageRequestDTO;
import com.verda.BE.chat.dto.responseDto.GetPreChatListDTO;
import com.verda.BE.chat.dto.responseDto.RecieveMessageResponseDTO;
import com.verda.BE.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @Operation(summary = "채팅방 입장시 이전 채팅목록 조회", description = "채팅방 입장시 이전 채팅들을 불러옴")
    @GetMapping("/chat/{roomId}")
    public GetPreChatListDTO getPreMessage(@PathVariable("roomId") long roomId) {
        GetPreChatListDTO preMessage = chatService.getPreMessage(roomId);
        return preMessage;
    }

    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/sub/chat/message/{roomId}")
    public RecieveMessageResponseDTO message(){
        return null;
    }
}
