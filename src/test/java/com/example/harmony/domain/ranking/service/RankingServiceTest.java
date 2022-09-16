package com.example.harmony.domain.ranking.service;

import com.example.harmony.domain.ranking.dto.RankingListResponse;
import com.example.harmony.domain.ranking.dto.RankingResponse;
import com.example.harmony.domain.user.model.Family;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.domain.user.repository.FamilyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @InjectMocks
    RankingService rankingService;

    @Mock
    FamilyRepository familyRepository;

    @Nested
    @DisplayName("랭킹 조회")
    class GetRankings {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("유저의 가족정보 X")
            void family_not_found() {
                // given
                Long familyId = -1L;

                Family family = Family.builder()
                        .id(familyId)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                when(familyRepository.findById(familyId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> rankingService.getRankings(user));

                // then
                assertEquals("404 NOT_FOUND \"유저의 가족정보를 찾을 수 없습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                Long familyId = 1L;

                Family family1 = Family.builder()
                        .id(familyId)
                        .weeklyScore(200)
                        .build();

                User user = User.builder()
                        .family(family1)
                        .build();

                Family family2 = Family.builder()
                        .id(2L)
                        .weeklyScore(100)
                        .build();

                Family family3 = Family.builder()
                        .id(3L)
                        .weeklyScore(400)
                        .build();

                Family family4 = Family.builder()
                        .id(4L)
                        .weeklyScore(400)
                        .build();

                List<Family> families = Arrays.asList(family3, family4, family1, family2);

                when(familyRepository.findById(familyId))
                        .thenReturn(Optional.of(user.getFamily()));

                when(familyRepository.findAllByOrderByWeeklyScoreDesc())
                        .thenReturn(families);

                when(familyRepository.findTop10ByOrderByWeeklyScoreDesc())
                        .thenReturn(families);

                // when
                RankingListResponse rankingListResponse = rankingService.getRankings(user);

                // then
                RankingResponse family = rankingListResponse.getFamily();
                assertEquals(3, family.getRanking());

                List<RankingResponse> top10 = rankingListResponse.getTop10();
                assertEquals(1, top10.get(0).getRanking());
                assertEquals(1, top10.get(1).getRanking());
                assertEquals(3, top10.get(2).getRanking());
                assertEquals(4, top10.get(3).getRanking());
            }
        }
    }
}