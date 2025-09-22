package com.moira.smallhabitbackend.user.controller;

import com.moira.smallhabitbackend.user.dto.request.SignupRequest;
import com.moira.smallhabitbackend.user.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final SignupService signupService;

    @GetMapping("/signup/check/email")
    ResponseEntity<Object> checkEmail(@RequestParam String email) {
        signupService.checkEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/signup/check/nickname")
    ResponseEntity<Object> checkNickname(@RequestParam String nickname) {
        signupService.checkNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/signup")
    ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        signupService.signup(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
