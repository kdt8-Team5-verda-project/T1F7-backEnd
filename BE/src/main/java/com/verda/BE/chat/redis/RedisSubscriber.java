package com.verda.BE.chat.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verda.BE.chat.dto.requestDto.ChatMessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations sendingOperations;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessageRequestDTO chatrequest = objectMapper.readValue(publishMessage, ChatMessageRequestDTO.class);

            sendingOperations.convertAndSend("/sub/api/chat/"+chatrequest.getRoomId(), chatrequest.getContent());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
