package com.project.mytv.adapter.out.jpa.video;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJpaRepository extends JpaRepository<VideoJpaEntity, String> {

    List<VideoJpaEntity> findByChannelId(String channelId);
}
