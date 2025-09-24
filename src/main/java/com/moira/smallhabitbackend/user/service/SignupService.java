package com.moira.smallhabitbackend.user.service;

import com.moira.smallhabitbackend.global.exception.ErrorCode;
import com.moira.smallhabitbackend.global.exception.custom.HabitUserException;
import com.moira.smallhabitbackend.global.utility.Encryptor;
import com.moira.smallhabitbackend.user.dto.request.SignupRequest;
import com.moira.smallhabitbackend.user.entity.User;
import com.moira.smallhabitbackend.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class SignupService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Encryptor encryptor;
    private final UserMapper userMapper;

    // 이메일 정규 표현식
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    // 비밀번호 정규 표현식 (8-24자, 대소문자, 숫자, 특수문자 각 1개 이상)
    private static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$");
    // 전화번호 정규 표현식 (010으로 시작하고 11자리)
    private static final Pattern PHONE_PATTERN
            = Pattern.compile("^010\\d{8}$");

    private void validate(SignupRequest request) {
        if (!StringUtils.hasText(request.email())) {
            throw new HabitUserException(ErrorCode.NO_EMAIL, HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(request.password())) {
            throw new HabitUserException(ErrorCode.NO_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(request.name())) {
            throw new HabitUserException(ErrorCode.NO_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(request.nickname())) {
            throw new HabitUserException(ErrorCode.NO_NICKNAME, HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(request.phone())) {
            throw new HabitUserException(ErrorCode.NO_PHONE, HttpStatus.BAD_REQUEST);
        }

        // 1. 이메일 형식 체크
        if (!EMAIL_PATTERN.matcher(request.email()).matches()) {
            throw new HabitUserException(ErrorCode.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }

        // 2. 비밀번호 형식 체크
        if (!PASSWORD_PATTERN.matcher(request.password()).matches()) {
            throw new HabitUserException(ErrorCode.INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        // 3. 휴대폰번호 형식 체크
        if (!PHONE_PATTERN.matcher(request.phone()).matches()) {
            throw new HabitUserException(ErrorCode.INVALID_PHONE, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public void checkNickname(String nickname) {
        // 닉네임 형식 확인
        if (!StringUtils.hasText(nickname)) {
            throw new HabitUserException(ErrorCode.NO_NICKNAME, HttpStatus.BAD_REQUEST);
        }

        // 닉네임 존재 여부 확인
        if (userMapper.checkNickname(nickname) > 0) {
            throw new HabitUserException(ErrorCode.USING_NICKNAME, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public void checkEmail(String email) {
        // 이메일 형식 확인
        if (!StringUtils.hasText(email)) {
            throw new HabitUserException(ErrorCode.NO_EMAIL, HttpStatus.BAD_REQUEST);
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new HabitUserException(ErrorCode.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }

        // 이메일 존재 여부 확인
        if (userMapper.checkEmail(email) > 0) {
            throw new HabitUserException(ErrorCode.USING_EMAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public void checkPhone(String phone) {
        // 휴대폰 형식 확인
        if (!StringUtils.hasText(phone)) {
            throw new HabitUserException(ErrorCode.NO_PHONE, HttpStatus.BAD_REQUEST);
        }

        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new HabitUserException(ErrorCode.INVALID_PHONE, HttpStatus.BAD_REQUEST);
        }

        // 휴대폰 존재 여부 확인
        if (userMapper.checkPhone(encryptor.encrypt(phone)) > 0) {
            throw new HabitUserException(ErrorCode.USING_PHONE, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void signup(SignupRequest request) {
        // 유효성 검사
        validate(request);

        // DTO -> 엔티티 변환
        User user = new User(request, bCryptPasswordEncoder, encryptor);

        // DB 저장
        userMapper.insertUser(user);
    }
}
