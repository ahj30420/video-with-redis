package com.project.mytv.adapter.out;

import com.project.mytv.application.port.out.CommentBlockPort;
import com.project.mytv.common.RedisKeyGenerator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentBlockPersistenceAdapter implements CommentBlockPort {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveUserCommentBlock(String userId, String commentId) {
        stringRedisTemplate.opsForSet()
                .add(RedisKeyGenerator.getUserCommentBlock(userId), commentId);
    }

    @Override
    public Set<String> getUserCommentBlocks(String userId) {
        return stringRedisTemplate.opsForSet()
                .members(RedisKeyGenerator.getUserCommentBlock(userId));
    }

}
