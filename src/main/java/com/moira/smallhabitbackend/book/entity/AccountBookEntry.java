package com.moira.smallhabitbackend.book.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountBookEntry {
    private String id;
    private String userId;
    private String groupId;
    private String content;
    private String description;
    private BigDecimal price;
    private LocalDate date;
    private AccountBookEntryType type;
    private AccountBookEntryCategory category;
    private AccountBookEntryMethod method;
    private LocalDateTime createdAt;
}