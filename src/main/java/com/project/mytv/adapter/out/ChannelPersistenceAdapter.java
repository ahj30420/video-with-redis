package com.project.mytv.adapter.out;

import com.project.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.project.mytv.adapter.out.jpa.channel.ChannelJpaRepository;
import com.project.mytv.adapter.out.redis.channel.ChannelRedisHash;
import com.project.mytv.adapter.out.redis.channel.ChannelRedisRepository;
import com.project.mytv.application.port.out.LoadChannelPort;
import com.project.mytv.application.port.out.SaveChannelPort;
import com.project.mytv.domain.channel.Channel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelPersistenceAdapter implements LoadChannelPort, SaveChannelPort {

    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelRedisRepository channelRedisRepository;

    @Override
    public void saveChannel(Channel channel) {
        channelRedisRepository.deleteById(channel.getId());
        channelRedisRepository.save(ChannelRedisHash.from(channel));

        channelJpaRepository.save(ChannelJpaEntity.from(channel));
    }

    @Override
    public Optional<Channel> loadChannel(String id) {
        return channelRedisRepository.findById(id)
                .map(ChannelRedisHash::toDomain)
                .or(() -> {
                    var optionalEntity = channelJpaRepository.findById(id);
                    optionalEntity.ifPresent(jpaEntity -> channelRedisRepository.save(ChannelRedisHash.from(jpaEntity.toDomain())));

                    return optionalEntity.map(ChannelJpaEntity::toDomain);
                });
    }
}
