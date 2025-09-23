package com.moira.smallhabitbackend.book.dto.response;

import java.time.LocalDateTime;

public record AccountBookGroupResponse(
        String groupId,
        String userId,
        String name,
        LocalDateTime createdAt
) {
}
