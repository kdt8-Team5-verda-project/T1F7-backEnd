package com.verda.BE.chat.controller;

import com.verda.BE.chat.dto.requestDto.ChatMessageRequestDTO;
import com.verda.BE.chat.dto.responseDto.GetTargetNameDTO;
import com.verda.BE.chat.dto.responseDto.RecieveMessageResponseDTO;
import com.verda.BE.chat.repository.PreChatInterface;
import com.verda.BE.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;
//    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 유저용 채팅방 이름 가져오기
     * @param roomId
     */
    @Operation(summary = "채팅방 이름 가져오기", description = "")
    @GetMapping("/api/chat/user/{roomId}")
    public GetTargetNameDTO getUserChatName(@PathVariable("roomId") long roomId){
        GetTargetNameDTO targetName = chatService.getUserChatName(roomId);
        return targetName;
    }

    /**
     * 펀드매니저용 채팅방 이름 가져오기
     * @param roomId
     */
    @Operation(summary = "채팅방 이름 가져오기", description = "")
    @GetMapping("/api/chat/fm/{roomId}")
    public GetTargetNameDTO getFmTargetName(@PathVariable("roomId") long roomId){
        GetTargetNameDTO targetName = chatService.getFmChatName(roomId);
        return targetName;
    }

    /**
     * 채팅방 입장시 이전 채팅목록 조회
     * @param roomId
     * @return
     */
    @Operation(summary = "채팅방 입장시 이전 채팅목록 조회", description = "채팅방 입장시 이전 채팅들을 불러옴")
    @GetMapping("/api/chat/{roomId}")
    public List<PreChatInterface> getPreMessage(@PathVariable("roomId") long roomId) {
        return chatService.getPreMessage(roomId);
    }

    @MessageMapping("/api/chat/room/entered")
    public void entered(@RequestBody ChatMessageRequestDTO chatMessageRequestDTO){
        template.convertAndSend("/sub/chat/room/"+chatMessageRequestDTO.getRoomId(),chatMessageRequestDTO.getContent());
    }

    @MessageMapping("/api/send/messages/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public RecieveMessageResponseDTO message(@RequestBody ChatMessageRequestDTO chatMessageRequestDTO){
        RecieveMessageResponseDTO recieveMessage = chatService.sendMessage(chatMessageRequestDTO);
        return recieveMessage;
    }
    
//    redis를 메시지 브로커로 하는 채팅
//    @MessageMapping("/api/chat")
//    public void message(@RequestBody ChatMessageRequestDTO chatMessageRequestDTO) {
//        RecieveMessageResponseDTO recieveMessage = chatService.sendMessage(chatMessageRequestDTO);
//        redisTemplate.convertAndSend("/sub/api/chat",chatMessageRequestDTO);
//    }

    /**
     * 테스트
     */
    @PostMapping("/api/save/chat")
    public void saveMessage(@RequestBody ChatMessageRequestDTO requestDTO){
        chatService.saveMessage(requestDTO);
    }
}