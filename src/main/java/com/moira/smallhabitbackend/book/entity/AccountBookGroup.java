package com.moira.smallhabitbackend.book.entity;

import com.moira.smallhabitbackend.book.dto.request.AccountBookCreateRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AccountBookGroup {
    private String id;
    private String userId;
    private String name;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public AccountBookGroup(AccountBookCreateRequest request, String userId, String code) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = request.name();
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }
}