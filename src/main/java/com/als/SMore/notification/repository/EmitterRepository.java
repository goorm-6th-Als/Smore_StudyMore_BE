package com.als.SMore.notification.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ListOperations<String, Object> listOperations;
//
//    public EmitterRepository(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.listOperations = redisTemplate.opsForList();
//    }

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String eventCacheId, Object event) {
        log.info("saveEventCache event : " + event.toString());
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByMemberPk(String memberPk) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().split("_")[0].equals(memberPk))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithByMemberPk(String memberPk) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().split("_")[0].equals(memberPk))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitters.remove(id);
    }



    public SseEmitter findByEmitterId(String emitterId) {
        return emitters.get(emitterId);
    }

}
