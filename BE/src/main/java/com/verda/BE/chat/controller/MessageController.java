package com.verda.BE.chat.controller;

import com.verda.BE.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;
    @GetMapping("/chat/{roomId}")
    public void getPreMessage(@PathVariable("roomId") long roomId){
        chatService.getPreMessage(roomId);
    }

}
