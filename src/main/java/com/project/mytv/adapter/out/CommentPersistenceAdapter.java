package com.project.mytv.adapter.out;

import com.project.mytv.adapter.out.mongo.comment.CommentDocument;
import com.project.mytv.adapter.out.mongo.comment.CommentMongoRepository;
import com.project.mytv.application.port.out.CommentPort;
import com.project.mytv.common.RedisKeyGenerator;
import com.project.mytv.domain.comment.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentPort {

    private final CommentMongoRepository commentMongoRepository;
    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public Comment saveComment(Comment comment) {
        var commentDocument = CommentDocument.from(comment);

        return commentMongoRepository.save(commentDocument)
                .toDomain();
    }

    @Override
    public Optional<Comment> loadComment(String commentId) {
        return commentMongoRepository.findById(commentId)
                .map(CommentDocument::toDomain);
    }

    @Override
    public void deleteComment(String commentId) {
        commentMongoRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> listComment(String videoId, String order, String offset, Integer maxSize) {
        return commentMongoRepository.findAllByVideoIdAndParentIdAndPublishedAtLessThanEqualOrderByPublishedAtDesc(
                        videoId, null, LocalDateTime.parse(offset), Limit.of(maxSize))
                .stream()
                .map(CommentDocument::toDomain)
                .toList();
    }

    @Override
    public List<Comment> listReply(String parentId, String offset, Integer maxSize) {
        return commentMongoRepository.findAllByParentIdAndPublishedAtLessThanEqualOrderByPublishedAtDesc(
                        parentId, LocalDateTime.parse(offset), Limit.of(maxSize))
                .stream()
                .map(CommentDocument::toDomain)
                .toList();
    }

    @Override
    public Optional<Comment> getPinnedComment(String videoId) {
        var commentId = stringRedisTemplate.opsForValue()
                .get(RedisKeyGenerator.getPinnedCommentKey(videoId));
        if (commentId == null) {
            return Optional.empty();
        }

        return commentMongoRepository.findById(commentId)
                .map(CommentDocument::toDomain);
    }
}
