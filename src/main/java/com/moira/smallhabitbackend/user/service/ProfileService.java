package com.moira.smallhabitbackend.user.service;

import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.global.utility.Encryptor;
import com.moira.smallhabitbackend.user.dto.response.UserProfileResponse;
import com.moira.smallhabitbackend.user.entity.User;
import com.moira.smallhabitbackend.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final Encryptor encryptor;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(SimpleUserAuth simpleUserAuth) {
        // 내 프로필 조회
        String userId = simpleUserAuth.userId();
        User user = userMapper.selectUserProfile(userId);

        // 휴대폰 번호 복호화
        String decryptedPhone = encryptor.decrypt(user.getPhone());

        return user.toUserProfileResponse(decryptedPhone);
    }
}
