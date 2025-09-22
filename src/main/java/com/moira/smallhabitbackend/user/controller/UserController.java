package com.moira.smallhabitbackend.user.controller;

import com.moira.smallhabitbackend.global.auth.SimpleUserAuth;
import com.moira.smallhabitbackend.global.auth.UserPrincipal;
import com.moira.smallhabitbackend.user.dto.request.LoginRequest;
import com.moira.smallhabitbackend.user.dto.request.SignupRequest;
import com.moira.smallhabitbackend.user.dto.response.TokenResponse;
import com.moira.smallhabitbackend.user.service.LoginService;
import com.moira.smallhabitbackend.user.service.LogoutService;
import com.moira.smallhabitbackend.user.service.SignupService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final static String RTK_COOKIE_NAME = "refreshToken";
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final SignupService signupService;

    private void putRtkInCookie(HttpServletResponse response, String rtk) {
        Cookie cookie = new Cookie(RTK_COOKIE_NAME, rtk);

        // cookie.setSecure(true);         // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.setHttpOnly(true);          // JavaScript로 접근 불가능
        cookie.setPath("/");               // 모든 경로에서 쿠키 사용 가능
        cookie.setMaxAge(60 * 60 * 24);    // 1일

        response.addCookie(cookie);
    }

    private void removeRtkFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(RTK_COOKIE_NAME, null);

        // cookie.setSecure(true);         // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.setHttpOnly(true);          // JavaScript로 접근 불가능
        cookie.setPath("/");               // 모든 경로에서 쿠키 사용 가능
        cookie.setMaxAge(0);               // 쿠키 만료

        response.addCookie(cookie);
    }

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

    @PostMapping("/login")
    ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        // IP 추출
        String ipAddress = httpServletRequest.getRemoteAddr();

        // 로그인 성공 후 atk, rtk 반환
        TokenResponse tokens = loginService.login(loginRequest, ipAddress);

        // rtk는 쿠키에 넣어준다.
        this.putRtkInCookie(httpServletResponse, tokens.rtk());

        // atk는 요청 본문으로 반환한다.
        return ResponseEntity.status(HttpStatus.OK).body(tokens.atk());
    }

    @PostMapping("/logout")
    ResponseEntity<Object> logout(
            @UserPrincipal SimpleUserAuth simpleUserAuth,
            HttpServletResponse httpServletResponse
    ) {
        // db에서 rtk를 삭제한다.
        logoutService.logout(simpleUserAuth);

        // 쿠키에 담긴 rtk를 초기화한다.
        this.removeRtkFromCookie(httpServletResponse);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
