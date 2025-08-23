package com.project.mytv.adapter.out;

import static com.project.mytv.common.CacheNames.VIDEO;

import com.project.mytv.adapter.out.jpa.video.VideoJpaEntity;
import com.project.mytv.adapter.out.jpa.video.VideoJpaRepository;
import com.project.mytv.application.port.out.LoadVideoPort;
import com.project.mytv.application.port.out.SaveVideoPort;
import com.project.mytv.domain.video.Video;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.project.mytv.common.CacheNames.VIDEO_LIST;
import static com.project.mytv.common.RedisKeyGenerator.getVideoViewCountKey;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements LoadVideoPort, SaveVideoPort {

    private final VideoJpaRepository videoJpaRepository;
    private final RedisTemplate<String, Long> redisTemplate;


    @Override
    @Cacheable(cacheNames = VIDEO, key = "#videoId")
    public Video loadVideo(String videoId) {
        return videoJpaRepository.findById(videoId)
                .map(VideoJpaEntity::toDomain)
                .orElseThrow();
    }

    @Override
    @Cacheable(cacheNames = VIDEO_LIST, key = "#channelId")
    public List<Video> loadVideoByChannel(String channelId) {
        return videoJpaRepository.findByChannelId(channelId).stream()
                .map(VideoJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Long getViewCount(String videoId) {
        var videoViewCountKey = getVideoViewCountKey(videoId);
        var viewCont = redisTemplate.opsForValue().get(videoViewCountKey);
        return viewCont == null ? 0 : viewCont;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = VIDEO_LIST, key = "#video.channelId"),
            @CacheEvict(cacheNames = VIDEO, key = "#video.id")
    })
    public void saveVideo(Video video) {
        videoJpaRepository.save(VideoJpaEntity.from(video));
    }

    @Override
    public void incrementViewCount(String videoId) {
        var videoViewCountKey = getVideoViewCountKey(videoId);
        redisTemplate.opsForValue().increment(videoViewCountKey);
    }
}
