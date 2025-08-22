package com.project.mytv.adapter.out;

import com.project.mytv.application.port.out.MessagePort;
import com.project.mytv.common.MessageTopics;
import com.project.mytv.domain.message.NewVideoMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePersistenceAdapter implements MessagePort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void sendNewVideMessage(String channelId) {
        redisTemplate.convertAndSend(MessageTopics.NEW_VIDEO, new NewVideoMessage(channelId));
    }
}
