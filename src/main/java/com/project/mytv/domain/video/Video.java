package com.project.mytv.domain.video;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Serializable {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String fileUrl;
    private String channelId;
    private Long viewCount;
    private Long likeCount;
    private LocalDateTime publishedAt;

    public void bindCount(long viewCount, long likeCount) {
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
}
