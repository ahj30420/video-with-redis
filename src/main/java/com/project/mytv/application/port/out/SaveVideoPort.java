package com.project.mytv.application.port.out;

import com.project.mytv.domain.video.Video;

public interface SaveVideoPort {

    void saveVideo(Video video);

    void incrementViewCount(String videoId);

    void syncViewCount(String videoId);

}
