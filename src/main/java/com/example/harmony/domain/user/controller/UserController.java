package com.example.harmony.domain.user.controller;

import com.example.harmony.domain.user.dto.SignupRequest;
import com.example.harmony.domain.user.service.UserService;
import com.example.harmony.global.common.SuccessResponse;
import com.example.harmony.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<?> emailChk(@RequestBody Map<String,String> map) {
        String msg = "이메일 중복체크 결과를 확인해주세요.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,userService.emailChk(map.get("email"))));
    }

    // 닉네임 중복체크
    @PostMapping("/api/nickname-check")
    public ResponseEntity<?> nicknameChk(@RequestBody Map<String,String> map) {
        String msg = "닉네임 중복체크 결과를 확인해주세요.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,userService.nicknameChk(map.get("nickname"))));
    }

    // 가족코드 입력
    @PutMapping("/api/family/join")
    public ResponseEntity<?> enterFamilyCode(@RequestBody Map<String,String> map,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = "가족 연결이 완료되었습니다.";
        userService.enterFamilyCode(map.get("familyCode"), userDetails);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg));
    }

    // 역할 설정
    @PutMapping("/api/user/role")
    public ResponseEntity<?> setRole(@RequestBody Map<String,String> map,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = "역할 설정을 완료하였습니다.";
        userService.setRole(map.get("role"), userDetails);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg));
    }

}