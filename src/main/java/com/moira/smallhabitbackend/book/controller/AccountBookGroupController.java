package com.moira.smallhabitbackend.book.controller;

import com.moira.smallhabitbackend.book.dto.request.AccountBookCreateRequest;
import com.moira.smallhabitbackend.book.service.AccountBookGroupService;
import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountBookGroupController {
    private final AccountBookGroupService accountBookGroupService;

    @PostMapping("/book/group")
    ResponseEntity<Object> createAccountBookGroup(@RequestBody AccountBookCreateRequest request, @UserPrincipal SimpleUserAuth simpleUserAuth) {
        accountBookGroupService.createAccountBookGroup(request, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
