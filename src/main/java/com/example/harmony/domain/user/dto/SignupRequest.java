package com.example.harmony.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Size(min=2, max = 20, message = "이름은 2~20자 내로 입력해주세요.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Size(min=2,max=20, message = "닉네임을 2~20자 내로 입력해주세요.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @Pattern(regexp= "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 영문, 숫자 및 특수문자를 포함한 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirmPassword;

    @NotNull(message = "성별을 선택해주세요.")
    private String gender;

}

