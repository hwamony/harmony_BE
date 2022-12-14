package com.example.harmony.domain.schedule.controller;

import com.example.harmony.domain.schedule.dto.ScheduleRequest;
import com.example.harmony.domain.schedule.service.ScheduleService;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.global.security.UserDetailsImpl;
import com.example.harmony.global.security.config.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ScheduleController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        })
@MockBean(JpaMetamodelMappingContext.class)
class ScheduleControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ScheduleService scheduleService;

    @Nested
    @DisplayName("?????? ??????")
    class PostSchedule {

        @Nested
        @DisplayName("??????")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("?????????????????? ????????????")
            void invalid_category() throws Exception {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("???????????? ?????? ????????????")
                        .title("??????")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .memberIds(Arrays.asList(1L, 2L))
                        .content("??????")
                        .build();

                String scheduleRequestJson = objectMapper.writeValueAsString(scheduleRequest);

                // when & then
                mvc.perform(post("/schedules")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleRequestJson))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("??????")
        class Success {

            @Test
            @WithUserDetails
            @DisplayName("?????? ?????????")
            void success() throws Exception {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .title("??????")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .memberIds(Arrays.asList(1L, 2L))
                        .content("??????")
                        .build();

                String scheduleRequestJson = objectMapper.writeValueAsString(scheduleRequest);

                // when & then
                mvc.perform(post("/schedules")
                                .with(csrf())
                                .with(user(new UserDetailsImpl(new User())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleRequestJson))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("?????? ??????")
    class ModifySchedule {

        @Nested
        @DisplayName("??????")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("?????????????????? ????????????")
            void invalid_category() throws Exception {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("???????????? ?????? ????????????")
                        .title("??????")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .memberIds(Arrays.asList(1L, 2L))
                        .content("??????")
                        .build();

                String scheduleRequestJson = objectMapper.writeValueAsString(scheduleRequest);

                // when & then
                mvc.perform(put("/schedules/{scheduleId}", "1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleRequestJson))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("??????")
        class Success {

            @Test
            @WithUserDetails
            @DisplayName("?????? ?????????")
            void success() throws Exception {
                // given
                ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                        .category("TRIP")
                        .title("??????")
                        .startDate(LocalDate.of(2022, 8, 8))
                        .endDate(LocalDate.of(2022, 8, 8))
                        .memberIds(Arrays.asList(1L, 2L))
                        .content("??????")
                        .build();

                String scheduleRequestJson = objectMapper.writeValueAsString(scheduleRequest);

                // when & then
                mvc.perform(put("/schedules/{scheduleId}", "1")
                                .with(csrf())
                                .with(user(new UserDetailsImpl(new User())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleRequestJson))
                        .andExpect(status().isOk());
            }
        }
    }
}