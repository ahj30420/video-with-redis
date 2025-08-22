package com.project.mytv.domain.message;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewVideoMessage implements Serializable {
    private String channelId;
}