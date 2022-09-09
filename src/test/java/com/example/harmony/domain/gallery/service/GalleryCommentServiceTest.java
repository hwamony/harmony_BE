package com.example.harmony.domain.gallery.service;

import com.example.harmony.domain.gallery.dto.GalleryCommentRequest;
import com.example.harmony.domain.gallery.entity.Gallery;
import com.example.harmony.domain.gallery.entity.GalleryComment;
import com.example.harmony.domain.gallery.repository.GalleryCommentRepository;
import com.example.harmony.domain.gallery.repository.GalleryRepository;
import com.example.harmony.domain.user.model.Family;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.domain.user.service.FamilyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GalleryCommentServiceTest {

    @InjectMocks
    GalleryCommentService galleryCommentService;

    @Mock
    GalleryRepository galleryRepository;

    @Mock
    GalleryCommentRepository galleryCommentRepository;

    @Mock
    FamilyService familyService;

    @Nested
    @DisplayName("갤러리 댓글 작성")
    class WriteGalleryComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 갤러리")
            void gallery_not_found() {
                // given
                Long galleryId = -1L;

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                User user = User.builder().build();

                when(galleryRepository.findById(galleryId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.writeGalleryComment(galleryId, galleryCommentRequest, user));

                // then
                assertEquals("404 NOT_FOUND \"갤러리를 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("가족구성원이 아닌 유저가 갤러리 댓글 작성 시도")
            void user_is_not_family_member() {
                // given
                Long galleryId = 1L;

                Family family1 = Family.builder()
                        .id(1L)
                        .build();

                Gallery gallery = Gallery.builder()
                        .family(family1)
                        .build();

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                Family family2 = Family.builder()
                        .id(2L)
                        .build();

                User user = User.builder()
                        .family(family2)
                        .build();

                when(galleryRepository.findById(galleryId))
                        .thenReturn(Optional.of(gallery));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.writeGalleryComment(galleryId, galleryCommentRequest, user));

                // then
                assertEquals("403 FORBIDDEN \"댓글 작성 권한이 없습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                Long galleryId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .id(1L)
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                Gallery gallery = Gallery.builder()
                        .family(family)
                        .comments(new ArrayList<>())
                        .build();

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                User user = User.builder()
                        .family(family)
                        .build();

                when(galleryRepository.findById(galleryId))
                        .thenReturn(Optional.of(gallery));

                doNothing().when(familyService).plusScore(family, 5);
                family.plusScore(5);

                // when
                assertDoesNotThrow(() -> galleryCommentService.writeGalleryComment(galleryId, galleryCommentRequest, user));

                // then
                assertEquals(totalScore + 5, family.getTotalScore());
                assertEquals(weeklyScore + 5, family.getWeeklyScore());
            }
        }
    }

    @Nested
    @DisplayName("갤러리 댓글 수정")
    class EditGalleryComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 갤러리 댓글")
            void galleryComment_not_found() {
                // given
                Long galleryCommentId = -1L;

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                User user = User.builder().build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.editGalleryComment(galleryCommentId, galleryCommentRequest, user));

                // then
                assertEquals("404 NOT_FOUND \"갤러리 댓글을 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("댓글 작성자가 아닌 유저가 수정 시도")
            void user_is_not_commenter() {
                // given
                Long galleryCommentId = 1L;

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                User user1 = User.builder()
                        .id(1L)
                        .build();

                GalleryComment galleryComment = GalleryComment.builder()
                        .user(user1)
                        .build();

                User user2 = User.builder()
                        .id(2L)
                        .build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.of(galleryComment));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.editGalleryComment(galleryCommentId, galleryCommentRequest, user2));

                // then
                assertEquals("403 FORBIDDEN \"댓글 수정 권한이 없습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                Long galleryCommentId = 1L;

                GalleryCommentRequest galleryCommentRequest = GalleryCommentRequest.builder().build();

                User user = User.builder()
                        .id(1L)
                        .build();

                GalleryComment galleryComment = GalleryComment.builder()
                        .user(user)
                        .build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.of(galleryComment));

                // when
                assertDoesNotThrow(() -> galleryCommentService.editGalleryComment(galleryCommentId, galleryCommentRequest, user));

                // then
                assertEquals(galleryCommentRequest.getContent(), galleryComment.getContent());
            }
        }
    }

    @Nested
    @DisplayName("갤러리 댓글 삭제")
    class DeleteGalleryComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("존재하지않는 갤러리 댓글")
            void galleryComment_not_found() {
                // given
                Long galleryCommentId = -1L;

                User user = User.builder().build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.empty());

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.deleteGalleryComment(galleryCommentId, user));

                // then
                assertEquals("404 NOT_FOUND \"갤러리 댓글을 찾을 수 없습니다\"", exception.getMessage());
            }

            @Test
            @DisplayName("댓글 작성자가 아닌 유저가 삭제 시도")
            void user_is_not_commenter() {
                // given
                Long galleryCommentId = 1L;

                User user1 = User.builder()
                        .id(1L)
                        .build();

                GalleryComment galleryComment = GalleryComment.builder()
                        .user(user1)
                        .build();

                User user2 = User.builder()
                        .id(2L)
                        .build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.of(galleryComment));

                // when
                Exception exception = assertThrows(ResponseStatusException.class, () -> galleryCommentService.deleteGalleryComment(galleryCommentId, user2));

                // then
                assertEquals("403 FORBIDDEN \"댓글 삭제 권한이 없습니다\"", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("정상 케이스")
            void success() {
                Long galleryCommentId = 1L;

                int totalScore = 1000;
                int weeklyScore = 100;

                Family family = Family.builder()
                        .totalScore(totalScore)
                        .weeklyScore(weeklyScore)
                        .build();

                User user = User.builder()
                        .id(1L)
                        .family(family)
                        .build();

                GalleryComment galleryComment = GalleryComment.builder()
                        .user(user)
                        .build();

                when(galleryCommentRepository.findById(galleryCommentId))
                        .thenReturn(Optional.of(galleryComment));

                doNothing().when(familyService).minusScore(family, 5);
                family.minusScore(5);

                // when
                assertDoesNotThrow(() -> galleryCommentService.deleteGalleryComment(galleryCommentId, user));

                // then
                assertEquals(totalScore - 5, user.getFamily().getTotalScore());
                assertEquals(weeklyScore - 5, user.getFamily().getWeeklyScore());
            }
        }
    }
}