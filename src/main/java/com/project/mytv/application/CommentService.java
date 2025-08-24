package com.project.mytv.application;

import com.project.mytv.adapter.in.api.dto.CommentRequest;
import com.project.mytv.adapter.in.api.dto.CommentResponse;
import com.project.mytv.application.port.in.CommentUseCase;
import com.project.mytv.application.port.out.CommentBlockPort;
import com.project.mytv.application.port.out.CommentLikePort;
import com.project.mytv.application.port.out.CommentPort;
import com.project.mytv.application.port.out.LoadUserPort;
import com.project.mytv.domain.comment.Comment;
import com.project.mytv.domain.user.User;
import com.project.mytv.exception.BadRequestException;
import com.project.mytv.exception.DomainNotFoundException;
import com.project.mytv.exception.ForbiddenRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentUseCase {

    private final CommentPort commentPort;
    private final LoadUserPort loadUserPort;
    private final CommentLikePort commentLikePort;
    private final CommentBlockPort commentBlockPort;

    public CommentService(CommentPort commentPort,
            @Qualifier("userCachePersistenceAdapter") LoadUserPort loadUserPort,
            CommentLikePort commentLikePort, CommentBlockPort commentBlockPort) {
        this.commentPort = commentPort;
        this.loadUserPort = loadUserPort;
        this.commentLikePort = commentLikePort;
        this.commentBlockPort = commentBlockPort;
    }

    @Override
    public Comment createComment(User user, CommentRequest commentRequest) {
        var comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .channelId(commentRequest.getChannelId())
                .videoId(commentRequest.getVideoId())
                .parentId(commentRequest.getParentId())
                .text(commentRequest.getText())
                .authorId(user.getId())
                .publishedAt(LocalDateTime.now())
                .build();

        return commentPort.saveComment(comment);
    }

    @Override
    public Comment updateComment(String commentId, User user, CommentRequest commentRequest) {
        var comment = commentPort.loadComment(commentId)
                .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));

        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
            throw new BadRequestException("Request metadata is invalid.");
        }
        if (!equalMetaData(comment, commentRequest)) {
            throw new BadRequestException("Request metadata is invalid.");
        }

        comment.updateText(commentRequest.getText());

        return commentPort.saveComment(comment);

    }

    private boolean equalMetaData(Comment comment, CommentRequest commentRequest) {
        return Objects.equals(comment.getChannelId(), commentRequest.getChannelId()) &&
                Objects.equals(comment.getVideoId(), commentRequest.getVideoId()) &&
                Objects.equals(comment.getParentId(), commentRequest.getParentId());
    }

    @Override
    public void deleteComment(String commentId, User user) {
        var comment = commentPort.loadComment(commentId)
                .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));

        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
            throw new ForbiddenRequestException("The request might not be properly authorized.");
        }

        commentPort.deleteComment(commentId);
    }

    @Override
    public CommentResponse getComment(String commentId) {
        var comment = commentPort.loadComment(commentId)
                .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));

        return buildComment(comment);
    }

    private CommentResponse buildComment(Comment comment) {
        var user = loadUserPort.loadUser(comment.getAuthorId())
                .orElse(User.defaultUser(comment.getAuthorId()));
        var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
        return CommentResponse.from(comment, user, commentLikeCount);
    }

    @Override
    public List<CommentResponse> listComments(String videoId, String order, String offset,
            Integer maxSize) {
        var list = commentPort.listComment(videoId, order, offset, maxSize).stream()
                .map(comment -> {
                    var user = loadUserPort.loadUser(comment.getAuthorId())
                            .orElse(User.defaultUser(comment.getAuthorId()));
                    var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
                    var replies = commentPort.listReply(comment.getId(), offset, 100).stream()
                            .map(this::buildComment)
                            .toList();
                    return CommentResponse.from(comment, user, commentLikeCount, replies);
                })
                .toList();
        commentPort.getPinnedComment(videoId)
                .ifPresent(pinnedComment -> {
                    var pinnedCommentResponse = buildComment(pinnedComment);
                    list.add(0, pinnedCommentResponse);
                });

        return list;
    }

    @Override
    public List<CommentResponse> listComments(User user, String videoId, String order,
            String offset, Integer maxSize) {
        var commentBlocks = commentBlockPort.getUserCommentBlocks(user.getId());

        return commentPort.listComment(videoId, order, offset, maxSize).stream()
                .filter(comment -> !commentBlocks.contains(comment.getId()))
                .map(comment -> {
                    var author = loadUserPort.loadUser(comment.getAuthorId())
                            .orElse(User.defaultUser(comment.getAuthorId()));
                    var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
                    var replies = commentPort.listReply(comment.getId(), offset, 100).stream()
                            .map(this::buildComment)
                            .toList();
                    return CommentResponse.from(comment, author, commentLikeCount, replies);
                })
                .toList();
    }

    @Override
    public List<CommentResponse> listReplies(String parentId, String offset, Integer maxSize) {
        return commentPort.listReply(parentId, offset, maxSize).stream()
                .map(this::buildComment)
                .toList();
    }
}
