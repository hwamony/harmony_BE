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
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        String msg = "회원가입을 성공하였습니다";
        userService.signup(request);
                return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg));
    }

    // 이메일 중복체크
    @PostMapping("/api/email-check")
    public ResponseEntity<?> emailChk(@RequestBody Map<String,String> email) {
        String msg = "이메일 중복체크 결과를 확인해주세요.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,userService.emailChk(email.get("email"))));
    }

    // 닉네임 중복체크
    @PostMapping("/api/nickname-check")
    public ResponseEntity<?> nicknameChk(@RequestBody Map<String,String> nickname) {
        String msg = "닉네임 중복체크 결과를 확인해주세요.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,userService.nicknameChk(nickname.get("nickname"))));
    }

//    // 역할 수정
//    @PutMapping("/api/user/role")

}