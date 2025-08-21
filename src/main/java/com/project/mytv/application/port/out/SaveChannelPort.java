package com.project.mytv.application.port.out;

import com.project.mytv.domain.channel.Channel;

public interface SaveChannelPort {
    void saveChannel(Channel channel);
}
