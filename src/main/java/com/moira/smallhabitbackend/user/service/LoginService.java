package com.moira.smallhabitbackend.user.service;

import com.moira.smallhabitbackend.global.auth.JwtProvider;
import com.moira.smallhabitbackend.global.exception.ErrorCode;
import com.moira.smallhabitbackend.global.exception.custom.HabitUserException;
import com.moira.smallhabitbackend.user.dto.request.LoginRequest;
import com.moira.smallhabitbackend.user.dto.response.TokenResponse;
import com.moira.smallhabitbackend.user.entity.User;
import com.moira.smallhabitbackend.user.entity.UserLoginHistory;
import com.moira.smallhabitbackend.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Transactional
    public TokenResponse login(LoginRequest request, String ipAddress) {
        // 유저 조회
        String email = request.email();
        User user = userMapper.selectUserByEmail(email);

        if (user == null || !bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
            throw new HabitUserException(ErrorCode.NOT_FOUND_USER, HttpStatus.BAD_REQUEST);
        }

        // 토큰 생성
        String atk = jwtProvider.createAtk(user);
        String rtk = jwtProvider.createRtk(user);

        // 로그인 기록 저장
        // TODO: 추후 userAgent와 DeviceId를 추출하는 로직 추가
        UserLoginHistory userLoginHistory = new UserLoginHistory(user, ipAddress, "", "");
        userMapper.updateUserLoginInfo(user, rtk);
        userMapper.insertUserLoginHistory(userLoginHistory);

        return new TokenResponse(atk, rtk);
    }
}
