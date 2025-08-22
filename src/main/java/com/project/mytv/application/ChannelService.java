package com.project.mytv.application;

import com.project.mytv.adapter.in.api.dto.ChannelRequest;
import com.project.mytv.application.port.in.ChannelUseCase;
import com.project.mytv.application.port.out.LoadChannelPort;
import com.project.mytv.application.port.out.SaveChannelPort;
import com.project.mytv.domain.channel.Channel;
import com.project.mytv.domain.channel.ChannelSnippet;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelService implements ChannelUseCase {

    private final LoadChannelPort loadChannelPort;
    private final SaveChannelPort saveChannelPort;

    @Override
    public Channel createChannel(ChannelRequest request) {
        var channel = Channel.builder()
                .id(UUID.randomUUID().toString())
                .snippet(
                        ChannelSnippet.builder()
                                .title(request.getSnippet().getTitle())
                                .description(request.getSnippet().getDescription())
                                .thumbnailUrl(request.getSnippet().getThumbnailUrl())
                                .publishedAt(LocalDateTime.now())
                                .build()
                )
                .build();

        saveChannelPort.saveChannel(channel);
        return channel;
    }

    @Override
    public Channel updateChannel(String channelId, ChannelRequest channelRequest) {
        var channel = loadChannelPort.loadChannel(channelId).get();
        channel.updateSnippet(channelRequest.getSnippet());

        saveChannelPort.saveChannel(channel);
        return null;
    }

    @Override
    public Channel getChannel(String id) {
        return null;
    }
}
