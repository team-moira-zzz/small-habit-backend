package com.moira.smallhabitbackend.book.dto.response;

public record AccountBookGroupUserResponse(
        String userId,
        String nickname,
        String joinedAt
) {
}
