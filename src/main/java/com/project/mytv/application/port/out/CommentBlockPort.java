package com.project.mytv.application.port.out;

import java.util.Set;

public interface CommentBlockPort {
    void saveUserCommentBlock(String userId, String commentId);

    Set<String> getUserCommentBlocks(String userId);
}
