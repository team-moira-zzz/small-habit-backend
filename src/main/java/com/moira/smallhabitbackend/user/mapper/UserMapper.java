package com.moira.smallhabitbackend.user.mapper;

import com.moira.smallhabitbackend.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 회원가입
    int checkEmail(String email);

    int checkNickname(String nickname);

    void insertUser(User user);
}
