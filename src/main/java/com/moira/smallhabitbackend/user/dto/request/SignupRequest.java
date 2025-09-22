package com.moira.smallhabitbackend.user.dto.request;

public record SignupRequest(
        String email,
        String password,
        String name,
        String nickname,
        String phone
) {
}