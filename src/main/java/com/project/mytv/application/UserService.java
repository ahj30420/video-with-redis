package com.project.mytv.application;

import com.project.mytv.application.port.in.UserUserCase;
import com.project.mytv.application.port.out.LoadUserPort;
import com.project.mytv.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUserCase {

    private final LoadUserPort loadUserPort;

    @Override
    public User getUser(String userId) {
        if(userId == null) {
            return null;
        }
        return loadUserPort.loadUser(userId)
                .orElse(null);
    }
}
