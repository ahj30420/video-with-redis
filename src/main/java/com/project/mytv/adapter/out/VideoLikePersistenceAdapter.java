package com.project.mytv.adapter.out;

import com.project.mytv.application.port.out.VideoLikePort;
import com.project.mytv.common.RedisKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoLikePersistenceAdapter implements VideoLikePort {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Long getVideoLikeCount(String videoId) {
        return stringRedisTemplate.opsForSet().size(RedisKeyGenerator.getVideoLikeKey(videoId));
    }

    @Override
    public Long addVideoLike(String videoId, String userId) {
        return stringRedisTemplate.opsForSet().add(RedisKeyGenerator.getVideoLikeKey(videoId), userId);
    }

    @Override
    public Long removeVideoLike(String videoId, String userId) {
        return stringRedisTemplate.opsForSet().remove(RedisKeyGenerator.getVideoLikeKey(videoId), userId);
    }

    @Override
    public boolean isVideoLikeMember(String videoId, String userId) {
        return stringRedisTemplate.opsForSet().isMember(RedisKeyGenerator.getVideoLikeKey(videoId), userId);
    }
}
