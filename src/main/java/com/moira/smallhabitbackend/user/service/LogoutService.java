package com.moira.smallhabitbackend.user.service;

import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogoutService {
    private final UserMapper userMapper;

    @Transactional
    public void logout(SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // rtk 삭제
        userMapper.updateUserLogoutInfo(userId);
    }
}
