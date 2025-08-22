package com.project.mytv.adapter.in.api;

import com.project.mytv.adapter.in.api.dto.ChannelRequest;
import com.project.mytv.adapter.in.api.dto.CommandResponse;
import com.project.mytv.application.port.in.ChannelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels")
public class ChannelApiController {

    private final ChannelUseCase channelUseCase;

    @PostMapping
    public CommandResponse createChannel(@RequestBody ChannelRequest channelRequest) {
        var channel = channelUseCase.createChannel(channelRequest);
        return new CommandResponse(channel.getId());
    }

    @PostMapping("{channelId}")
    public void updateChannel(
            @PathVariable String channelId,
            @RequestBody ChannelRequest channelRequest
    ) {
      channelUseCase.updateChannel(channelId, channelRequest);
    }
}
