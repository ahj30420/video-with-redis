package com.project.mytv.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoRequest {
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelId;
}