package com.example.harmony.domain.ranking.service;

import com.example.harmony.domain.ranking.dto.RankingResponse;
import com.example.harmony.domain.user.model.Family;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.domain.user.repository.FamilyRepository;
import com.example.harmony.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    FamilyRepository familyRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RankingService rankingService;

    @Nested
    @DisplayName("랭킹 조회")
    class getRanking {

        @Nested
        @DisplayName("성공")
        class success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                //given
                Family family1 = Family.builder()
                        .id(1L)
                        .familyName("와랄라 멘탈")
                        .weeklyScore(20)
                        .totalScore(300)
                        .flower(false)
                        .build();
                Family family2 = Family.builder()
                        .id(2L)
                        .familyName("순이네")
                        .weeklyScore(80)
                        .totalScore(1370)
                        .flower(true)
                        .build();
                Family family3 = Family.builder()
                        .id(3L)
                        .familyName("짱구네")
                        .weeklyScore(130)
                        .totalScore(2760)
                        .flower(true)
                        .build();
                Family family4 = Family.builder()
                        .id(4L)
                        .familyName("탄이네")
                        .weeklyScore(0)
                        .totalScore(0)
                        .flower(false)
                        .build();

                User user = User.builder()
                        .family(family3)
                        .build();

                List<Family> familyList = Arrays.asList(family1, family2, family3, family4);

                when(familyRepository.findAll()).thenReturn(familyList);

                // when
                RankingResponse rankingResponse = rankingService.getFamilyScore(user);

                //then
                assertEquals(10, rankingResponse.getTop10List().size());
                assertEquals(1, rankingResponse.getRanking());
                assertEquals(true, rankingResponse.getFlower());
                assertEquals(130, rankingResponse.getWeeklyScore());
                assertEquals(3, rankingResponse.getLevel());
            }
        }

    }
}
