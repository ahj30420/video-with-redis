package com.project.mytv.application.port.in;

import com.project.mytv.adapter.in.api.dto.CommentRequest;
import com.project.mytv.adapter.in.api.dto.CommentResponse;
import com.project.mytv.domain.comment.Comment;
import com.project.mytv.domain.user.User;
import java.util.List;

public interface CommentUseCase {

    Comment createComment(User user, CommentRequest commentRequest);

    Comment updateComment(String commentId, User user, CommentRequest commentRequest);

    void deleteComment(String commentId, User user);

    CommentResponse getComment(String commentId);

    List<CommentResponse> listComments(String videoId, String order, String offset, Integer maxSize);

    List<CommentResponse> listComments(User user, String videoId, String order, String offset, Integer maxSize);

    List<CommentResponse> listReplies(String parentId, String offset, Integer maxSize);
}
