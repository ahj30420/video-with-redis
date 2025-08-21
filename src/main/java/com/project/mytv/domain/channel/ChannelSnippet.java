package com.project.mytv.domain.channel;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChannelSnippet {
    private String title;
    private String description;
    private String thumbnailUrl;
    private LocalDateTime publishedAt;
}
