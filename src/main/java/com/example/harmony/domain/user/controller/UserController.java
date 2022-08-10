package com.example.harmony.domain.user.controller;

import com.example.harmony.domain.user.dto.SignupRequest;
import com.example.harmony.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/signup")
    public void signup(@RequestBody @Valid SignupRequest request) { userService.signup(request); }

    // 이메일 중복체크
    @PostMapping("/api/email-check")
    public boolean emailChk(@RequestBody String email) {
        boolean exist = userService.emailChk(email);
        return exist;
    }

    // 닉네임 중복체크
    @PostMapping("/api/nickname-check")
    public boolean nicknameChk(@RequestBody String nickname) {
        boolean exist = userService.nicknameChk(nickname);
        return exist;
    }

}