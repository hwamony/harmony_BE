package com.example.harmony.domain.user.service;

import com.example.harmony.domain.user.dto.FamilyInfoResponse;
import com.example.harmony.domain.user.dto.MembersResponse;
import com.example.harmony.domain.user.entity.Family;
import com.example.harmony.domain.user.entity.RoleEnum;
import com.example.harmony.domain.user.entity.User;
import com.example.harmony.domain.user.repository.FamilyRepository;
import com.example.harmony.domain.user.repository.UserRepository;
import com.example.harmony.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    // 가족코드 생성
    public Map<String,String> createFamily(String familyName, UserDetailsImpl userDetails) {
        // 랜덤코드 생성
        Random random = new Random();
        String familyCode = random.ints(48,123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
                .toString();

        // 가족객체 생성 및 저장
        Family family = new Family(familyName,familyCode);
        familyRepository.save(family);
        User user = userDetails.getUser();
        user.setFamily(family);
        userRepository.save(user);

        // 랜덤코드 반환
        Map<String,String> map = new HashMap<>();
        map.put("familyCode",familyCode);
        return map;
    }

    // 가족 정보 조회
    @Transactional
    public FamilyInfoResponse getFamily(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Family family = user.getFamily();

        // 가족 이름
        String familyName = family.getFamilyName();

        // 가족 리스트
        List<User> members = family.getMembers();
        List<MembersResponse> memberList = new ArrayList<>();
        for(User member : members) {
            // 가족 멤버 중 역할 미설정자
            if(member.getRole()==null) {
                member.setRole(RoleEnum.NOBODY);
            }

            MembersResponse dto = MembersResponse.builder()
                    .userId(member.getId())
                    .name(member.getName())
                    .role(member.getRole().getRole())
                    .build();

            memberList.add(dto);
        }

        return new FamilyInfoResponse(familyName,memberList);
    }

}
