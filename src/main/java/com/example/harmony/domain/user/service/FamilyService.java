package com.example.harmony.domain.user.service;

import com.example.harmony.domain.notification.model.NotificationRequest;
import com.example.harmony.domain.notification.service.NotificationService;
import com.example.harmony.domain.user.dto.FamilyInfoResponse;
import com.example.harmony.domain.user.dto.MembersResponse;
import com.example.harmony.domain.user.model.Family;
import com.example.harmony.domain.user.model.RoleEnum;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.domain.user.repository.FamilyRepository;
import com.example.harmony.domain.user.repository.UserRepository;
import com.example.harmony.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

    // 가족코드 생성
    public Map<String, String> createFamily(String familyName, UserDetailsImpl userDetails) {
        // 가족이름 중복체크
        if (familyRepository.findByFamilyName(familyName).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 가족이름입니다.");
        }

        // 랜덤코드 생성
        Random random = new Random();
        String familyCode = random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // 가족객체 생성 및 저장
        Family family = new Family(familyName, familyCode);
        familyRepository.save(family);
        User user = userDetails.getUser();
        user.setFamily(family);
        userRepository.save(user);

        // 랜덤코드 반환
        Map<String, String> map = new HashMap<>();
        map.put("familyCode", familyCode);
        return map;
    }

    // 가족 정보 조회
    @Transactional
    public FamilyInfoResponse getFamily(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Family family = user.getFamily();

        // 가족 리스트
        List<User> members = family.getMembers();
        List<MembersResponse> memberList = new ArrayList<>();
        for (User member : members) {
            if (!member.isActive()) {
                continue;
            }
            
            // 가족 멤버 중 역할 미설정자
            if (member.getRole() == null) {
                member.setRole(RoleEnum.NOBODY);
            }

            MembersResponse dto = MembersResponse.builder()
                    .userId(member.getId())
                    .name(member.getName())
                    .role(member.getRole().getRole())
                    .build();

            memberList.add(dto);
        }

        return new FamilyInfoResponse(family, memberList);
    }

    // 가족코드 조회
    public Map<String, String> getFamilyCode(User user) {
        Map<String, String> result = new HashMap<>();
        String familyCode = user.getFamily().getFamilyCode();
        result.put("email", user.getEmail());
        result.put("familyCode", familyCode);

        return result;
    }

    public void plusScore(Family family, int score) {
        int oldLevel = family.getLevel();
        family.plusScore(score);
        familyRepository.save(family);
        int newLevel = family.getLevel();
        if (newLevel > oldLevel) {
            notificationService.createNotification(new NotificationRequest("level", "up"), family.getMembers());
        }
    }

    public void minusScore(Family family, int score) {
        family.minusScore(score);
        familyRepository.save(family);
    }
}
