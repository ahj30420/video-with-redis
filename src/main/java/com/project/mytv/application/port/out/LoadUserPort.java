package com.project.mytv.application.port.out;

import com.project.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.project.mytv.domain.user.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;

public interface LoadUserPort {
    Optional<User> loadUser(String userId);

    List<User> loadAllUsers(List<String> userIds);
}
