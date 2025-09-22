package com.moira.smallhabitbackend.global.auth;

public record SimpleUserAuth(
        String userId,
        String email,
        String role,
        String nickname
) {
}
