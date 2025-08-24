package com.project.mytv.application.port.in;

public interface VideoLikeUseCase {

    Long likeVideo(String videoId, String userId);

    Long unlikeVdieo(String videoId, String userId);

    boolean isLikedVideo(String videoId, String userId);
}
