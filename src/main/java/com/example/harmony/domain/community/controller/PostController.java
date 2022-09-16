package com.example.harmony.domain.community.controller;

import com.example.harmony.domain.community.dto.PostRequest;
import com.example.harmony.domain.community.service.PostService;
import com.example.harmony.global.common.SuccessResponse;
import com.example.harmony.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping(value = "/posts")
    public ResponseEntity<?> createPost(
            @ModelAttribute @Valid PostRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = postService.createPost(request, userDetails.getUser());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK,msg));
    }

    // 게시글 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String msg= "게시글 조회를 성공하였습니다.";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, msg, postService.getPost(postId,userDetails.getUser())));
    }

    // 게시글 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam String category,
                                      @RequestParam int page,
                                      @RequestParam int size,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = "게시글 목록 조회를 성공하였습니다";
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, msg, postService.getPosts(category, page, size,userDetails.getUser())));
    }

    // 게시글 수정
    @PutMapping(value = "/posts/{postId}")
    public ResponseEntity<?> putPost(@PathVariable Long postId,
                                     @ModelAttribute @Valid PostRequest request,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = postService.putPost(postId, request, userDetails.getUser());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, msg));
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String msg = postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, msg));
    }
}
