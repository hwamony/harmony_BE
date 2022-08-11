package com.example.harmony.domain.user.controller;

import com.example.harmony.domain.user.dto.SignupRequest;
import com.example.harmony.domain.user.service.UserService;
import com.example.harmony.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        userService.signup(request);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"회원가입을 성공하였습니다."));
    }

    // 이메일 중복체크
    @PostMapping("/api/email-check")
    public ResponseEntity<?> emailChk(@RequestBody String email) {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"이메일 중복체크를 성공하였습니다.",userService.emailChk(email)));
    }

    // 닉네임 중복체크
    @PostMapping("/api/nickname-check")
    public ResponseEntity<?> nicknameChk(@RequestBody String nickname) {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"닉네임 중복체크를 성공하였습니다.",userService.nicknameChk(nickname)));
    }

}