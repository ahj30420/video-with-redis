package com.project.mytv.adapter.in.api;

import com.project.mytv.adapter.in.api.dto.CommandResponse;
import com.project.mytv.adapter.in.api.dto.VideoRequest;
import com.project.mytv.application.port.in.VideoUseCase;
import com.project.mytv.domain.video.Video;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/videos")
public class VideoApiController {

    private final VideoUseCase videoUseCase;

    @GetMapping("{videoId}")
    public Video getVideo(@PathVariable String videoId) {
        return videoUseCase.getVideo(videoId);
    }

    @GetMapping(params = "channelId")
    public List<Video> listVideo(@RequestParam String channelId) {
        return videoUseCase.listVideos(channelId);
    }

    @PostMapping
    public CommandResponse createVideo(@RequestBody VideoRequest videoRequest) {
        var video = videoUseCase.createVideo(videoRequest);
        return new CommandResponse(video.getId());
    }

    @PostMapping("{videoId}/view")
    public void increaseVideoViewCount(@PathVariable String videoId) {
        videoUseCase.increaseVideoViewCount(videoId);
    }
}
