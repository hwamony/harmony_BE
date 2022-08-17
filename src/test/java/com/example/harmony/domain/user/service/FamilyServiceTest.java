package com.example.harmony.domain.user.service;

import com.example.harmony.domain.user.dto.FamilyInfoResponse;
import com.example.harmony.domain.user.entity.Family;
import com.example.harmony.domain.user.entity.RoleEnum;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FamilyServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    FamilyRepository familyRepository;

    @Nested
    @DisplayName("가족코드 생성")
    class createFamily {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                User user = User.builder().build();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                String familyName = "우당탕탕 순이네";

                FamilyService familyService = new FamilyService(familyRepository, userRepository);

                // when
                Map<String, String> result = familyService.createFamily(familyName, userDetails);

                // then
                assertEquals(user.getFamily().getFamilyCode(), result.get("familyCode"));
            }
        }
    }

    @Nested
    @DisplayName("가족코드 생성")
    class getFamily {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                String familyName = "우당탕탕 순이네";
                List<User> members = new ArrayList<>();
                Family family = Family.builder()
                        .familyName(familyName)
                        .members(members)
                        .build();

                User user = User.builder()
                        .id(1L)
                        .name("홍길동")
                        .role(RoleEnum.FATHER)
                        .family(family)
                        .build();

                User user1 = User.builder()
                        .id(2L)
                        .name("이영희")
                        .role(RoleEnum.MOTHER)
                        .family(family)
                        .build();

                members.add(user);
                members.add(user1);

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                FamilyService familyService = new FamilyService(familyRepository, userRepository);

                // when
                FamilyInfoResponse result = familyService.getFamily(userDetails);

                // then
                assertEquals(familyName,result.getFamilyName());
                assertEquals("홍길동",result.getMembers().get(0).getName());
                assertEquals("이영희",result.getMembers().get(1).getName());
            }

            @Test
            @DisplayName("멤버 중 역할 미설정자")
            void successWithNoRole() {
                // given
                String familyName = "우당탕탕 순이네";
                List<User> members = new ArrayList<>();
                Family family = Family.builder()
                        .familyName(familyName)
                        .members(members)
                        .build();

                User user = User.builder()
                        .id(1L)
                        .name("홍길동")
                        .family(family)
                        .build();

                User user1 = User.builder()
                        .id(2L)
                        .name("이영희")
                        .role(RoleEnum.MOTHER)
                        .family(family)
                        .build();

                members.add(user);
                members.add(user1);

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                FamilyService familyService = new FamilyService(familyRepository, userRepository);

                // when
                FamilyInfoResponse result = familyService.getFamily(userDetails);

                // then
                assertEquals(familyName,result.getFamilyName());
                assertEquals(RoleEnum.NOBODY.getRole(),result.getMembers().get(0).getRole());
                assertEquals(RoleEnum.MOTHER.getRole(),result.getMembers().get(1).getRole());
            }
        }
    }




}