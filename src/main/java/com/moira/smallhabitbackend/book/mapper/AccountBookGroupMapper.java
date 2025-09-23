package com.moira.smallhabitbackend.book.mapper;

import com.moira.smallhabitbackend.book.entity.AccountBookGroup;
import com.moira.smallhabitbackend.book.entity.AccountBookGroupUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountBookGroupMapper {
    // 그룹 생성
    int selectAccountBookGroupUserChk(String userId);

    void createAccountBookGroup(AccountBookGroup accountBookGroup);

    void insertAccountBookGroupUser(AccountBookGroupUser accountBookGroupUser);
}
