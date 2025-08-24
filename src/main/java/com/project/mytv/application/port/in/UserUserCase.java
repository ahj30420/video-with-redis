package com.project.mytv.application.port.in;

import com.project.mytv.domain.user.User;

public interface UserUserCase {
    User getUser(String userId);
}
