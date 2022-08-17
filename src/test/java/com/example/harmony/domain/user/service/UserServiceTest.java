package com.example.harmony.domain.user.service;

import com.example.harmony.domain.user.dto.CheckResponse;
import com.example.harmony.domain.user.dto.SignupRequest;
import com.example.harmony.domain.user.entity.Family;
import com.example.harmony.domain.user.entity.User;
import com.example.harmony.domain.user.repository.FamilyRepository;
import com.example.harmony.domain.user.repository.UserRepository;
import com.example.harmony.global.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    FamilyRepository familyRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("이메일 중복체크")
    class emailCheck {

        @Nested
        @DisplayName("실패")
        class Fails {

            @Test
            @DisplayName("이메일 빈 값")
            void blank() {
                // given
                String email = "";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.emailChk(email));

                // then
                assertEquals("400 BAD_REQUEST \"이메일을 입력해주세요.\"",exception.getMessage());
            }

            @Test
            @DisplayName("잘못된 이메일 형식")
            void notEmail() {
                // given
                String email = "soonie";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.emailChk(email));

                // then
                assertEquals("400 BAD_REQUEST \"이메일 형식이 아닙니다.\"",exception.getMessage());
            }

            @Test
            @DisplayName("이미 존재하는 이메일")
            void isExist() {
                // given
                String email = "soonie@baegopa.com";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(userRepository.findByEmail(email))
                        .thenReturn(Optional.of(User.builder().build()));

                // when
                CheckResponse checkResponse = userService.emailChk(email);

                // then
                assertFalse(checkResponse.isEnable());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                String email = "soonie@baegopa.com";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                CheckResponse checkResponse = userService.emailChk(email);

                // then
                assertTrue(checkResponse.isEnable());
            }
        }
    }

    @Nested
    @DisplayName("닉네임 중복체크")
    class nicknameCheck {

        @Nested
        @DisplayName("실패")
        class Fails {

            @Test
            @DisplayName("닉네임 빈 값")
            void blank() {
                // given
                String nickname = "";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.nicknameChk(nickname));

                // then
                assertEquals("400 BAD_REQUEST \"닉네임을 입력해주세요.\"",exception.getMessage());
            }

            @Test
            @DisplayName("길이 2~20자 이외")
            void overLength() {
                // given
                String nickname = "순";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.nicknameChk(nickname));

                // then
                assertEquals("400 BAD_REQUEST \"닉네임은 2~20자 내로 입력해야합니다.\"",exception.getMessage());
            }

            @Test
            @DisplayName("이미 존재하는 닉네임")
            void isExist() {
                // given
                String nickname = "나는순이";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(userRepository.findByNickname(nickname))
                        .thenReturn(Optional.of(User.builder().build()));

                // when
                CheckResponse checkResponse = userService.nicknameChk(nickname);

                // then
                assertFalse(checkResponse.isEnable());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                String nickname = "나는순이";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                CheckResponse checkResponse = userService.nicknameChk(nickname);

                // then
                assertTrue(checkResponse.isEnable());
            }
        }
    }

    @Nested
    @DisplayName("회원가입")
    class signup {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("이미 존재하는 이메일")
            void emailIsExist() {
                // given
                String email = "soonie@baegopa.com";
                String name = "권순";
                String nickname = "나는순이";
                String password = "abcd1234%^";
                String passwordConfirm = "abcd1234%^";
                String gender = "male";

                SignupRequest signupRequest = SignupRequest.builder()
                        .email(email)
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .passwordConfirm(passwordConfirm)
                        .gender(gender)
                        .build();

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(userRepository.findByEmail(email))
                        .thenReturn(Optional.of(User.builder().build()));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.signup(signupRequest));

                // then
                assertEquals("400 BAD_REQUEST \"이미 존재하는 이메일입니다.\"",exception.getMessage());
            }

            @Test
            @DisplayName("이미 존재하는 닉네임")
            void nicknameIsExist() {
                // given
                String email = "soonie@baegopa.com";
                String name = "권순";
                String nickname = "나는순이";
                String password = "abcd1234%^";
                String passwordConfirm = "abcd1234%^";
                String gender = "male";

                SignupRequest signupRequest = SignupRequest.builder()
                        .email(email)
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .passwordConfirm(passwordConfirm)
                        .gender(gender)
                        .build();

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(userRepository.findByNickname(nickname))
                        .thenReturn(Optional.of(User.builder().build()));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.signup(signupRequest));

                // then
                assertEquals("400 BAD_REQUEST \"이미 존재하는 닉네임입니다.\"",exception.getMessage());
            }

            @Test
            @DisplayName("비밀번호 확인 불일치")
            void doubleCheckFail() {
                // given
                String email = "soonie@baegopa.com";
                String name = "권순";
                String nickname = "나는순이";
                String password = "abcd1234%^";
                String passwordConfirm = "abcd1234";
                String gender = "male";

                SignupRequest signupRequest = SignupRequest.builder()
                        .email(email)
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .passwordConfirm(passwordConfirm)
                        .gender(gender)
                        .build();

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.signup(signupRequest));

                // then
                assertEquals("400 BAD_REQUEST \"비밀번호가 일치하지 않습니다.\"",exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                String email = "soonie@baegopa.com";
                String name = "권순";
                String nickname = "나는순이";
                String password = "abcd1234%^";
                String passwordConfirm = "abcd1234%^";
                String gender = "male";

                SignupRequest signupRequest = SignupRequest.builder()
                        .email(email)
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .passwordConfirm(passwordConfirm)
                        .gender(gender)
                        .build();

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                String result = userService.signup(signupRequest);

                // then
                assertEquals("회원가입을 성공하였습니다.", result);
            }
        }
    }

    @Nested
    @DisplayName("가족코드 입력")
    class enterFamilyCode {

        @Nested
        @DisplayName("실패")
        class Fails {

            @Test
            @DisplayName("유효하지 않은 가족코드")
            void invalidCode() {
                // given
                User user = User.builder().build();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                String familyCode = "AS43dg$f6GFg";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(familyRepository.findByFamilyCode(familyCode))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> userService.enterFamilyCode(familyCode,userDetails));

                // then
                assertEquals("404 NOT_FOUND \"유효한 가족코드가 아닙니다.\"",exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                User user = User.builder().build();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                String familyCode = "AS43dg$f6GFg";
                Family family = Family.builder()
                        .familyCode(familyCode)
                        .build();

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                when(familyRepository.findByFamilyCode(familyCode))
                        .thenReturn(Optional.of(family));
                
                // when
                String result = userService.enterFamilyCode(familyCode,userDetails);

                // then
                assertEquals("가족 연결이 완료되었습니다.", result);
                assertEquals(familyCode,user.getFamily().getFamilyCode());
            }
        }
    }

    @Nested
    @DisplayName("역할 설정")
    class setRole {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                User user = User.builder().build();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                String role = "아빠";

                UserService userService = new UserService(userRepository, familyRepository, passwordEncoder);

                // when
                String result = userService.setRole(role, userDetails);

                // then
                assertEquals("역할 설정을 완료하였습니다.", result);
            }
        }
    }

}