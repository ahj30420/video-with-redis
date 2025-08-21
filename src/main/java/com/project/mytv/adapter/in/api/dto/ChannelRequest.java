package com.project.mytv.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelRequest {
    private ChannelSnippetRequest snippet;
    private String contentOwnerId;
}
