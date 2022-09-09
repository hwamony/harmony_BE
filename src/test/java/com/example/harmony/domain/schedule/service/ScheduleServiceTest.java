package com.example.harmony.domain.schedule.service;

import com.example.harmony.domain.gallery.entity.Gallery;
import com.example.harmony.domain.schedule.dto.MonthlyScheduleResponse;
import com.example.harmony.domain.schedule.dto.ScheduleRequest;
import com.example.harmony.domain.schedule.model.Category;
import com.example.harmony.domain.schedule.model.Participation;
import com.example.harmony.domain.schedule.model.Schedule;
import com.example.harmony.domain.schedule.repository.ParticipationRepository;
import com.example.harmony.domain.schedule.repository.ScheduleRepository;
import com.example.harmony.domain.user.model.Family;
import com.example.harmony.domain.user.model.RoleEnum;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.domain.user.repository.UserRepository;
import com.example.harmony.domain.user.service.FamilyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ParticipationRepository participationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FamilyService familyService;

    @Nested
    @DisplayName("월별 일정 조회")
    class GetMonthlySchedule {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                int year = 2022;
                int month = 8;

                LocalDate from = LocalDate.of(year, month, 1).minusDays(1);
                LocalDate to = LocalDate.of(year, month, 1).plusMonths(1);

                Long familyId = 1L;
                Family family = Family.builder()
                        .id(familyId)
                        .build();

                User user = User.builder()
                        .family(family)
                        .role(RoleEnum.FIRST)
                        .build();

                Participation participation1 = Participation.builder()
                        .participant(user)
                        .build();

                Schedule schedule1 = Schedule.builder()
                        .category(Category.TRIP)
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .participations(new ArrayList<>(Arrays.asList(participation1)))
                        .galleries(Collections.emptyList())
                        .build();

                Schedule schedule2 = Schedule.builder()
                        .category(Category.ETC)
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .participations(new ArrayList<>(Arrays.asList(participation1)))
                        .galleries(Collections.emptyList())
                        .build();

                List<Schedule> schedules = new ArrayList<>(Arrays.asList(schedule1, schedule2));

                when(scheduleRepository.findAllByFamilyIdAndStartDateBeforeAndEndDateAfter(familyId, to, from))
                        .thenReturn(schedules);

                // when
                MonthlyScheduleResponse monthlyScheduleResponse = scheduleService.getMonthlySchedule(year, month, user);

                // then
                assertEquals(null, monthlyScheduleResponse.getCounts().get(Category.EAT_OUT));
                assertEquals(1, monthlyScheduleResponse.getCounts().get(Category.TRIP));
                assertEquals(null, monthlyScheduleResponse.getCounts().get(Category.COOK));
                assertEquals(null, monthlyScheduleResponse.getCounts().get(Category.CLEAN));
                assertEquals(1, monthlyScheduleResponse.getCounts().get(Category.ETC));
            }
        }
    }

    @Nested
    @DisplayName("일정 등록")
    class RegisterSchedule {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("종료일이 시작일보다 과거")
            void endDate_is_before_startDate() {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 7))
                        .build();

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.registerSchedule(scheduleRequest, user));

                // then
                assertEquals("400 BAD_REQUEST \"종료일은 시작일 이후여야 합니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("일정 참여인원 중 가족구성원이 아닌 유저 포함")
            void not_family_members() {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .build();

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                User user1 = User.builder()
                        .family(family1)
                        .build();

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                User user2 = User.builder()
                        .family(family2)
                        .build();

                Schedule schedule = new Schedule(scheduleRequest, user1.getFamily());

                when(scheduleRepository.save(any(Schedule.class)))
                        .thenReturn(schedule);

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.registerSchedule(scheduleRequest, user1));

                // then
                assertEquals("400 BAD_REQUEST \"가족 구성원만 일정에 참여할 수 있습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("종료일이 현재 이후")
            void endDate_is_after_or_equals_now() {
                // given
                LocalDate today = LocalDate.now();

                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .startDate(today)
                        .endDate(today)
                        .build();

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user1 = User.builder()
                        .family(family)
                        .build();

                User user2 = User.builder()
                        .family(family)
                        .build();

                Schedule schedule = new Schedule(scheduleRequest, user1.getFamily());

                when(scheduleRepository.save(any(Schedule.class)))
                        .thenReturn(schedule);

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

                // when
                assertDoesNotThrow(() -> scheduleService.registerSchedule(scheduleRequest, user1));

                // then
                assertFalse(schedule.isDone());
            }

            @Test
            @DisplayName("종료일이 현재보다 과거")
            void endDate_is_before_now() {
                // given
                LocalDate yesterday = LocalDate.now().minusDays(1);

                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .startDate(yesterday)
                        .endDate(yesterday)
                        .build();

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user1 = User.builder()
                        .family(family)
                        .build();

                User user2 = User.builder()
                        .family(family)
                        .build();

                Schedule schedule = new Schedule(scheduleRequest, user1.getFamily());

                when(scheduleRepository.save(any(Schedule.class)))
                        .thenReturn(schedule);

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

                // when
                assertDoesNotThrow(() -> scheduleService.registerSchedule(scheduleRequest, user1));

                // then
                assertTrue(schedule.isDone());
            }
        }
    }

    @Nested
    @DisplayName("일정 수정")
    class ModifySchedule {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 일정")
            void schedule_not_found() {
                // given
                Long scheduleId = -1L;

                ScheduleRequest scheduleRequest = ScheduleRequest.builder().build();

                User user = User.builder().build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.modifySchedule(scheduleId, scheduleRequest, user));

                // then
                assertEquals("404 NOT_FOUND \"일정을 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("가족구성원이 아닌 유저가 일정 수정 시도")
            void user_is_not_family_member() {
                // given
                Long scheduleId = 1L;

                ScheduleRequest scheduleRequest = ScheduleRequest.builder().build();

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family1)
                        .build();

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                Schedule schedule = Schedule.builder()
                        .family(family2)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.modifySchedule(scheduleId, scheduleRequest, user));

                // then
                assertEquals("403 FORBIDDEN \"일정 수정 권한이 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("일정 참여인원 중 가족구성원이 아닌 유저 포함")
            void participants_are_not_family_members() {
                // given
                Long scheduleId = 1L;

                ScheduleRequest scheduleRequest = ScheduleRequest.builder().build();

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                User user1 = User.builder()
                        .family(family1)
                        .build();

                Schedule schedule = Schedule.builder()
                        .family(family1)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                User user2 = User.builder()
                        .family(family2)
                        .build();

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.modifySchedule(scheduleId, scheduleRequest, user1));

                // then
                assertEquals("400 BAD_REQUEST \"가족 구성원만 일정에 참여할 수 있습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("종료일이 시작일보다 과거")
            void endDate_is_before_startDate() {
                Long scheduleId = 1L;

                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 7))
                        .build();

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .done(false)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user)));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.modifySchedule(scheduleId, scheduleRequest, user));

                // then
                assertEquals("400 BAD_REQUEST \"종료일은 시작일 이후여야 합니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                // given
                Long scheduleId = 1L;

                LocalDate today = LocalDate.now();

                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .startDate(today)
                        .endDate(today)
                        .memberIds(Arrays.asList(1L, 2L))
                        .build();

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user1 = User.builder()
                        .id(1L)
                        .family(family)
                        .build();

                User user2 = User.builder()
                        .id(2L)
                        .family(family)
                        .build();

                LocalDate yesterday = LocalDate.now().minusDays(1);

                Participation participation = Participation.builder()
                        .participant(user1)
                        .build();

                Schedule schedule = Schedule.builder()
                        .category(Category.TRIP)
                        .startDate(yesterday)
                        .endDate(yesterday)
                        .participations(Arrays.asList(participation))
                        .family(family)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                when(userRepository.findAllById(scheduleRequest.getMemberIds()))
                        .thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

                // when
                assertDoesNotThrow(() -> scheduleService.modifySchedule(scheduleId, scheduleRequest, user1));

                // then
                assertEquals(Category.valueOf(scheduleRequest.getCategory()), schedule.getCategory());
                assertEquals(scheduleRequest.getTitle(), schedule.getTitle());
                assertEquals(scheduleRequest.getStartDate(), schedule.getStartDate());
                assertEquals(scheduleRequest.getEndDate(), schedule.getEndDate());
                assertEquals(scheduleRequest.getMemberIds(), schedule.getParticipations().stream().map(x -> x.getParticipant().getId()).collect(Collectors.toList()));
            }
        }
    }

    @Nested
    @DisplayName("일정 삭제")
    class DeleteSchedule {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 일정")
            void schedule_not_found() {
                // given
                Long scheduleId = -1L;

                User user = User.builder().build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals("404 NOT_FOUND \"일정을 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("가족구성원이 아닌 유저가 일정 삭제 시도")
            void user_is_not_family_member() {
                // given
                Long scheduleId = 1L;

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family1)
                        .build();

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                Schedule schedule = Schedule.builder()
                        .family(family2)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals("403 FORBIDDEN \"일정 삭제 권한이 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("연동된 갤러리가 존재")
            void gallery_exists() {
                // given
                Long scheduleId = 1L;

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Gallery gallery = Gallery.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .galleries(Arrays.asList(gallery))
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals("400 BAD_REQUEST \"일정에 연동된 갤러리가 있습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("참여인원이 2명 이상인 완료된 일정")
            void two_or_more_participants_and_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation1 = Participation.builder().build();

                Participation participation2 = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation1, participation2))
                        .done(true)
                        .galleries(Collections.emptyList())
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                doNothing().when(familyService).minusScore(family, 10);
                family.minusScore(10);

                // when
                assertDoesNotThrow(() -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals(totalScore - 10, family.getTotalScore());
                assertEquals(weeklyScore - 10, family.getWeeklyScore());
            }

            @Test
            @DisplayName("참여인원이 2명 이상인 완료되지않은 일정")
            void two_or_more_participants_and_not_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation1 = Participation.builder().build();

                Participation participation2 = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation1, participation2))
                        .done(false)
                        .galleries(Collections.emptyList())
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                assertDoesNotThrow(() -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals(totalScore, family.getTotalScore());
                assertEquals(weeklyScore, family.getWeeklyScore());
            }

            @Test
            @DisplayName("참여인원이 1명인 완료된 일정")
            void one_participant_and_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation))
                        .done(true)
                        .galleries(Collections.emptyList())
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                assertDoesNotThrow(() -> scheduleService.deleteSchedule(scheduleId, user));

                // then
                assertEquals(totalScore, family.getTotalScore());
                assertEquals(weeklyScore, family.getWeeklyScore());
            }

            @Test
            @DisplayName("참여인원이 1명인 완료되지않은 일정")
            void one_participant_and_not_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user1 = User.builder()
                        .family(family)
                        .build();

                Participation participation = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation))
                        .done(false)
                        .galleries(Collections.emptyList())
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                assertDoesNotThrow(() -> scheduleService.deleteSchedule(scheduleId, user1));

                // then
                assertEquals(totalScore, family.getTotalScore());
                assertEquals(weeklyScore, family.getWeeklyScore());
            }
        }
    }

    @Nested
    @DisplayName("일정 완료여부 설정")
    class SetScheduleDone {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 일정")
            void schedule_not_found() {
                // given
                Long scheduleId = -1L;

                User user = User.builder().build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals("404 NOT_FOUND \"일정을 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("가족구성원이 아닌 유저가 일정 완료여부 설정 시도")
            void user_is_not_family_member() {
                // given
                Long scheduleId = 1L;

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family1)
                        .build();

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                Schedule schedule = Schedule.builder()
                        .family(family2)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals("403 FORBIDDEN \"일정 완료여부 설정 권한이 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("종료일이 지나지않은 일정 완료 시도")
            void endDate_is_future() {
                // given
                Long scheduleId = 1L;

                LocalDate tomorrow = LocalDate.now().plusDays(1);

                Family family = Family.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Schedule schedule = Schedule.builder()
                        .endDate(tomorrow)
                        .family(family)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals("400 BAD_REQUEST \"종료일이 현재 이전인 일정만 완료할 수 있습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("참여인원이 2명 이상인 일정 완료")
            void two_or_more_participants_and_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation1 = Participation.builder().build();

                Participation participation2 = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .endDate(LocalDate.of(2022, 8, 8))
                        .family(family)
                        .participations(Arrays.asList(participation1, participation2))
                        .done(false)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                doNothing().when(familyService).plusScore(family, 10);
                family.plusScore(10);

                // when
                assertDoesNotThrow(() -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals(totalScore + 10, family.getTotalScore());
                assertEquals(weeklyScore + 10, family.getWeeklyScore());
                assertTrue(schedule.isDone());
            }

            @Test
            @DisplayName("참여인원이 2명 이상인 일정 완료 취소")
            void two_or_more_participants_and_not_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation1 = Participation.builder().build();

                Participation participation2 = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation1, participation2))
                        .done(true)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                doNothing().when(familyService).minusScore(family, 10);
                family.minusScore(10);

                // when
                assertDoesNotThrow(() -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals(totalScore - 10, family.getTotalScore());
                assertEquals(weeklyScore - 10, family.getWeeklyScore());
                assertFalse(schedule.isDone());
            }

            @Test
            @DisplayName("참여인원이 1명인 완료된 일정")
            void one_participant_and_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .family(family)
                        .build();

                Participation participation = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .endDate(LocalDate.of(2022, 8, 8))
                        .family(family)
                        .participations(Arrays.asList(participation))
                        .done(false)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                assertDoesNotThrow(() -> scheduleService.setScheduleDone(scheduleId, user));

                // then
                assertEquals(totalScore, family.getTotalScore());
                assertEquals(weeklyScore, family.getWeeklyScore());
                assertTrue(schedule.isDone());
            }

            @Test
            @DisplayName("참여인원이 1명인 완료되지않은 일정")
            void one_participant_and_not_done() {
                // given
                Long scheduleId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user1 = User.builder()
                        .family(family)
                        .build();

                Participation participation = Participation.builder().build();

                Schedule schedule = Schedule.builder()
                        .family(family)
                        .participations(Arrays.asList(participation))
                        .done(true)
                        .build();

                when(scheduleRepository.findById(scheduleId))
                        .thenReturn(Optional.of(schedule));

                // when
                assertDoesNotThrow(() -> scheduleService.setScheduleDone(scheduleId, user1));

                // then
                assertEquals(totalScore, family.getTotalScore());
                assertEquals(weeklyScore, family.getWeeklyScore());
                assertFalse(schedule.isDone());
            }
        }
    }
}