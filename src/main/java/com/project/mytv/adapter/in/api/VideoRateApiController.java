package com.project.mytv.adapter.in.api;

import com.project.mytv.adapter.in.api.dto.VideoRateResponse;
import com.project.mytv.application.port.in.VideoLikeUseCase;
import com.project.mytv.domain.user.User;
import com.project.mytv.domain.video.VideoRate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/videos/rate")
public class VideoRateApiController {

    private final VideoLikeUseCase videoLikeUseCase;

    @PostMapping
    public void rateVideo(
            User user,
            @RequestParam String videoId,
            @RequestParam VideoRate rating
    ) {
        switch (rating) {
            case like:
                videoLikeUseCase.likeVideo(videoId, user.getId());
                break;
            case none:
                videoLikeUseCase.unlikeVdieo(videoId, user.getId());
                break;
        }
    }

    @GetMapping
    public VideoRateResponse getRate(
            User user,
            @RequestParam String videoId
    ) {
        var rate = videoLikeUseCase.isLikedVideo(videoId, user.getId()) ? VideoRate.like : VideoRate.none;
        return new VideoRateResponse(videoId, rate);
    }

}
