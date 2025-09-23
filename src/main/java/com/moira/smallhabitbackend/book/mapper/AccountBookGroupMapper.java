package com.moira.smallhabitbackend.book.mapper;

import com.moira.smallhabitbackend.book.dto.response.AccountBookGroupResponse;
import com.moira.smallhabitbackend.book.dto.response.AccountBookGroupUserResponse;
import com.moira.smallhabitbackend.book.entity.AccountBookGroup;
import com.moira.smallhabitbackend.book.entity.AccountBookGroupUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountBookGroupMapper {
    // 그룹 정보 조회
    AccountBookGroupResponse selectAccountBookGroup(String userId);

    List<AccountBookGroupUserResponse> selectAccountBookGroupUser(String groupId);

    // 그룹 생성 및 가입
    int selectAccountBookGroupUserChk(String userId);

    void createAccountBookGroup(AccountBookGroup accountBookGroup);

    void insertAccountBookGroupUser(AccountBookGroupUser accountBookGroupUser);
}
