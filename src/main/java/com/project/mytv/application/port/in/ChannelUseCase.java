package com.project.mytv.application.port.in;

import com.project.mytv.adapter.in.api.dto.ChannelRequest;
import com.project.mytv.domain.channel.Channel;

public interface ChannelUseCase {
    Channel createChannel(ChannelRequest channelRequest);
    Channel updateChannel(String channelId, ChannelRequest channelRequest);
    Channel getChannel(String id);
}
