package com.project.mytv.application;

import com.project.mytv.application.port.in.VideoLikeUseCase;
import com.project.mytv.application.port.out.VideoLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoLikeService implements VideoLikeUseCase {

    private final VideoLikePort videoLikePort;

    @Override
    public Long likeVideo(String videoId, String userId) {
        return videoLikePort.addVideoLike(videoId, userId);
    }

    @Override
    public Long unlikeVdieo(String videoId, String userId) {
        return videoLikePort.removeVideoLike(videoId, userId);
    }

    @Override
    public boolean isLikedVideo(String videoId, String userId) {
        return videoLikePort.isVideoLikeMember(videoId, userId);
    }
}
