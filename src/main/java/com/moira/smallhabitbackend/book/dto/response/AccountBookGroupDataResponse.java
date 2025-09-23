package com.moira.smallhabitbackend.book.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountBookGroupDataResponse {
    String groupId;
    String userId;
    String name;
    LocalDateTime createdAt;
    List<AccountBookGroupUserResponse> users;

    public AccountBookGroupDataResponse(AccountBookGroupResponse group, List<AccountBookGroupUserResponse> users) {
        this.groupId = group.groupId();
        this.userId = group.userId();
        this.name = group.name();
        this.createdAt = group.createdAt();
        this.users = users;
    }
}
