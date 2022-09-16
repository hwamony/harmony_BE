package com.example.harmony.domain.gallery.controller;

import com.example.harmony.domain.gallery.service.ImageService;
import com.example.harmony.domain.user.model.User;
import com.example.harmony.global.security.UserDetailsImpl;
import com.example.harmony.global.security.config.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ImageController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        })
@MockBean(JpaMetamodelMappingContext.class)
class ImageControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ImageService imageService;

    @Nested
    @DisplayName("갤러리 이미지 추가")
    class PostImages {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("이미지가 아닌 파일")
            void not_image_file() throws Exception {
                // given
                MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "텍스트파일.png", MimeTypeUtils.IMAGE_PNG_VALUE, (byte[]) null);
                MockMultipartFile textFile = new MockMultipartFile("imageFiles", "텍스트파일.txt", MimeTypeUtils.TEXT_PLAIN_VALUE, (byte[]) null);

                // when & then
                mvc.perform(multipart("/galleries/{galleryId}/images", "1")
                                .file(imageFile)
                                .file(textFile)
                                .with(csrf())
                        )
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithUserDetails
            @DisplayName("정상 케이스")
            void success() throws Exception {
                // given
                MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "텍스트파일.png", MimeTypeUtils.IMAGE_PNG_VALUE, (byte[]) null);

                // when & then
                mvc.perform(multipart("/galleries/{galleryId}/images", "1")
                                .file(imageFile)
                                .with(csrf())
                                .with(user(new UserDetailsImpl(new User())))
                        )
                        .andExpect(status().isCreated());
            }
        }
    }
}