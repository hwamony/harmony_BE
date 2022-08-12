package com.example.harmony.domain.user.controller;

import com.example.harmony.domain.user.service.FamilyService;
import com.example.harmony.global.common.SuccessResponse;
import com.example.harmony.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FamilyController {

    private final FamilyService familyService;

    // 가족코드 생성
    @PostMapping("/api/families")
    public ResponseEntity<?> createFamily(@RequestBody Map<String,String> map ,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = "가족코드를 생성하였습니다.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,familyService.createFamily(map.get("familyName"),userDetails)));
    }

    // 가족 정보 조회
    @GetMapping("/api/family")
    public ResponseEntity<?> getFamilyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = "가족 조회를 성공하였습니다.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg,familyService.getFamily(userDetails)));
    }


}
