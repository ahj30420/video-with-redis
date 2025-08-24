package com.project.mytv.adapter.out;

import static com.project.mytv.common.RedisKeyGenerator.getCommentLikeKey;

import com.project.mytv.application.port.out.CommentLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikePersistenceAdapter  implements CommentLikePort {

    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    public Long getCommentLikeCount(String commentId) {
        var likeCount = redisTemplate.opsForValue().get(getCommentLikeKey(commentId));
        return likeCount == null ? 0 : likeCount;
    }
}
