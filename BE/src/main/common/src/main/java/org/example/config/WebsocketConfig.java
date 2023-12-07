package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket    //웹소켓 서버 사용
@EnableWebSocketMessageBroker   //STOMP 사용
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final StompHandler stompHandler;

    /**
     * enableSimpleBroker는 /sub으로 시작하는 대상을 구독하는 클라이언트 모두에게 메시지를 브로드 캐스트함.(서버 -> 클라이언트)
     * setApplicationDestinationPrefixes는 /pub로 시작하는 주소를 사용하여 클라이언트에서 서버로 메시지를 전송 (클라이언트 -> 서버)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");    // 해당 경로로 들어오는것을 구독하는것으로 정한다.
        registry.setApplicationDestinationPrefixes("/pub");  // @MessageMapping("hello") 라면 경로는 -> /pub/hello
    }

    /**
     * 처음 websocket에 접속할때 통신을 담당할 엔드포인트 지정 
     * websocket을 지원하지 않는 브라우저는 SockJs사용
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss")     // ex ) ws://localhost:8080/stomp/chat
                .setAllowedOrigins("*");// 허용하는 도메인 주소 ('*')모두 허용
    }

    /**
     * StompHandler가 websocket접속 전, token을 체크함.
     * 사용자 인증로직 보류
     * @param registration
     */
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }

}
