package com.project.mytv.application.port.in;

import com.project.mytv.adapter.in.api.dto.VideoRequest;
import com.project.mytv.domain.video.Video;
import java.util.List;

public interface VideoUseCase {
    Video getVideo(String videoId);

    List<Video> listVideos(String channelId);

    Video createVideo(VideoRequest videoRequest);

    void increaseVideoViewCount(String videoId);
}
