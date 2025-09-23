package com.moira.smallhabitbackend.book.service;

import com.moira.smallhabitbackend.book.dto.request.AccountBookCreateRequest;
import com.moira.smallhabitbackend.book.entity.AccountBookGroup;
import com.moira.smallhabitbackend.book.entity.AccountBookGroupUser;
import com.moira.smallhabitbackend.book.mapper.AccountBookGroupMapper;
import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.global.exception.ErrorCode;
import com.moira.smallhabitbackend.global.exception.custom.HabitUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class AccountBookGroupService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final AccountBookGroupMapper accountBookGroupMapper;

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    private void validate(String userId) {
        int result = accountBookGroupMapper.selectAccountBookGroupUserChk(userId);

        if (result > 0) {
            throw new HabitUserException(ErrorCode.ALREADY_JOINED_USER, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void createAccountBookGroup(AccountBookCreateRequest request, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();
        String code = this.generateRandomCode();

        // 유효성 검사
        validate(userId);

        // DTO -> 엔티티 변환
        AccountBookGroup accountBookGroup = new AccountBookGroup(request, userId, code);
        AccountBookGroupUser accountBookGroupUser = new AccountBookGroupUser(accountBookGroup.getId(), userId);

        // DB 저장
        accountBookGroupMapper.createAccountBookGroup(accountBookGroup);
        accountBookGroupMapper.insertAccountBookGroupUser(accountBookGroupUser);
    }
}
