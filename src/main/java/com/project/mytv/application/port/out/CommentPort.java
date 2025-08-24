package com.project.mytv.application.port.out;

import com.project.mytv.domain.comment.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentPort {

    Comment saveComment(Comment comment);

    Optional<Comment> loadComment(String commentId);

    void deleteComment(String commentId);

    List<Comment> listComment(String videoId, String order, String offset, Integer maxSize);

    List<Comment> listReply(String parentId, String offset, Integer maxSize);

    Optional<Comment> getPinnedComment(String videoId);
}
