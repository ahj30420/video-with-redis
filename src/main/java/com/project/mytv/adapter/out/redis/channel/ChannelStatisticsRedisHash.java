package com.project.mytv.adapter.out.redis.channel;

import com.project.mytv.domain.channel.ChannelStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("channelStatistics")
@AllArgsConstructor
@Getter
public class ChannelStatisticsRedisHash {
    private long videoCount;
    private long subscriberCount;
    private long commentCount;

    public static ChannelStatisticsRedisHash from(ChannelStatistics statistics) {
        return new ChannelStatisticsRedisHash(statistics.getVideoCount(), statistics.getSubscriberCount(), statistics.getCommentCount());
    }

    public ChannelStatistics toDomain() {
        return ChannelStatistics.builder()
            .videoCount(this.getVideoCount())
            .subscriberCount(this.getSubscriberCount())
            .commentCount(this.getCommentCount())
            .build();
    }
}
