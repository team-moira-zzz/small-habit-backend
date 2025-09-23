package com.moira.smallhabitbackend.book.controller;

import com.moira.smallhabitbackend.book.dto.request.AccountBookCreateRequest;
import com.moira.smallhabitbackend.book.dto.response.AccountBookGroupDataResponse;
import com.moira.smallhabitbackend.book.service.AccountBookGroupService;
import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountBookGroupController {
    private final AccountBookGroupService accountBookGroupService;

    @GetMapping("/book/group")
    ResponseEntity<AccountBookGroupDataResponse> getMyGroup(@UserPrincipal SimpleUserAuth simpleUserAuth) {
        AccountBookGroupDataResponse response = accountBookGroupService.getAccountBookGroup(simpleUserAuth);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/book/group")
    ResponseEntity<Object> createAccountBookGroup(@RequestBody AccountBookCreateRequest request, @UserPrincipal SimpleUserAuth simpleUserAuth) {
        accountBookGroupService.createAccountBookGroup(request, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/book/group/{groupId}")
    ResponseEntity<Object> registerAccountBookGroup(@PathVariable String groupId, @UserPrincipal SimpleUserAuth simpleUserAuth) {
        accountBookGroupService.registerAccountBookGroup(groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
