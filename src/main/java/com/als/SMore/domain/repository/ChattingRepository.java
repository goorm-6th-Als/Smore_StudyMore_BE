package com.als.SMore.domain.repository;

import com.als.SMore.study.Chatting.DTO.ChatMessage;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public class ChattingRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ListOperations<String, Object> listOperations;

    public ChattingRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    private static final String CHAT_PREFIX = "chatting : ";

    public List<Object> getChatHistory(String studyPk) {
        String key = CHAT_PREFIX + studyPk;
        System.out.println("ChattingRepository.getChatHistory , key : " + key);
        return listOperations.range(key, 0, -1);
    }

    public void saveChatMessage(String studyPk, ChatMessage chatMessage) {
        String key = CHAT_PREFIX + studyPk;
        System.out.println("ChattingRepository.saveChatMessage , key : " + key);

        listOperations.rightPush(key, chatMessage);
        redisTemplate.expire(key, Duration.ofMinutes(2)); //일단 2분으로 설정, 실제 배포후에는 길게 변경
    }

}
