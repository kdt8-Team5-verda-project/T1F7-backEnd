package com.verda.BE.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@EnableCaching
public class RedisConfig {
    private final ObjectMapper objectMapper;

    public RedisConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(host, port)
        );
    }

    /**
     * redis cache 매니저
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                );

        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("Message",
                redisCacheConfiguration.entryTtl(Duration.ofMinutes(5)));
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }

//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory redisConnectionFactory,
//            MessageListenerAdapter messageListenerAdapter,
//            ChannelTopic channelTopic
//    ) {
//        RedisMessageListenerContainer redisMessageListenerContainer=new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, channelTopic);
//        return redisMessageListenerContainer;
//    }
//
//    @Bean
//    public MessageListenerAdapter messageListenerAdapter(RedisSubscriber subscriber){
//        return new MessageListenerAdapter(subscriber, "onMessage");
//    }
//
//    @Bean
//    public ChannelTopic channelTopic(){
//        return new ChannelTopic("/sub/api/chat");
//    }
//
//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, Object>redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
//
//        return redisTemplate;
//    }

}
