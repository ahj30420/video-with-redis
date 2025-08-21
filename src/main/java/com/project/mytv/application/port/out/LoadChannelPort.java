package com.project.mytv.application.port.out;

import com.project.mytv.domain.channel.Channel;
import java.util.Optional;

public interface LoadChannelPort {
    Optional<Channel> loadChannel(String id);
}
