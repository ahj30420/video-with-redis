package com.project.mytv.application.port.out;

public interface VideoLikePort {
    Long getVideoLikeCount(String videoId);

    Long addVideoLike(String videoId, String userId);

    Long removeVideoLike(String videoId, String userId);

    boolean isVideoLikeMember(String videoId, String userId);
}
