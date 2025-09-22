package com.moira.smallhabitbackend.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private UserRole role;
    private UserStatus status;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String rtk;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
