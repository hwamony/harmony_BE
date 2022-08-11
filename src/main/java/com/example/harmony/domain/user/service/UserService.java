package com.example.harmony.domain.user.service;

import com.example.harmony.domain.user.dto.SignupRequest;
import com.example.harmony.domain.user.entity.User;
import com.example.harmony.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void signup(SignupRequest requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();

        // 이메일 중복체크
        if(userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        // 닉네임 중복체크
        if(userRepository.findByNickname(nickname).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다.");
        }
        // 비밀번호 일치 여부
        if(!password.equals(requestDto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(new User(email, requestDto.getName(), nickname, encodedPassword, requestDto.getGender()));
    }

    // 이메일 중복체크
    public Map<String, Object> emailChk(String email) {
        Map<String, Object> data = new HashMap<>();
        data.put("exist",userRepository.findByEmail(email).isEmpty());
        return data;
    }

    // 닉네임 중복체크
    public Map<String,Object> nicknameChk(String nickname) {
        Map<String, Object> data = new HashMap<>();
        data.put("exist",userRepository.findByNickname(nickname).isEmpty());
        return data;
    }
}
