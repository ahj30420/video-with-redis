package com.project.mytv.adapter.out;

import com.project.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.project.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.project.mytv.application.port.out.LoadUserPort;
import com.project.mytv.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> loadUser(String userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public List<User> loadAllUsers(List<String> userIds) {
        var userJpaEntities = userJpaRepository.findAllById(userIds);
        return StreamSupport.stream(userJpaEntities.spliterator(), false)
            .map(UserJpaEntity::toDomain)
            .toList();
    }
}
