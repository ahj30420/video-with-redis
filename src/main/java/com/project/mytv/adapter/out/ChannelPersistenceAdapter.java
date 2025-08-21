package com.project.mytv.adapter.out;

import com.project.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.project.mytv.adapter.out.jpa.channel.ChannelJpaRepository;
import com.project.mytv.adapter.out.redis.channel.ChannelRedisHash;
import com.project.mytv.adapter.out.redis.channel.ChannelRedisRepository;
import com.project.mytv.application.port.out.SaveChannelPort;
import com.project.mytv.domain.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelPersistenceAdapter implements SaveChannelPort {

    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelRedisRepository channelRedisRepository;

    @Override
    public void saveChannel(Channel channel) {
        channelRedisRepository.deleteById(channel.getId());
        channelRedisRepository.save(ChannelRedisHash.from(channel));

        channelJpaRepository.save(ChannelJpaEntity.from(channel));
    }
}
