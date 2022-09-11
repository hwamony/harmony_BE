package com.example.harmony.domain.community.service;

import com.example.harmony.domain.community.model.Post;
import com.example.harmony.domain.community.model.PostComment;
import com.example.harmony.domain.community.repository.PostCommentRepository;
import com.example.harmony.domain.community.repository.PostRepository;
import com.example.harmony.domain.notification.model.NotificationRequest;
import com.example.harmony.domain.notification.service.NotificationService;
import com.example.harmony.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;

    private final NotificationService notificationService;

    // 게시글 댓글 작성
    public String createComment(Long postId, String content, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"게시물이 존재하지 않습니다.")
        );
        noBlank(content);

        PostComment postComment = new PostComment(content, post, user);
        postCommentRepository.save(postComment);

        notificationService.createNotification(new NotificationRequest("comment", "create"), Collections.singletonList(post.getUser()));
        return "댓글 작성을 성공하였습니다.";
    }

    // 게시글 댓글 수정
    public String putComment(Long commentId, String content, User user) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.")
        );
        noBlank(content);

        // 댓글 작성자 여부
        User writer = postComment.getUser();
        getAuthority(writer,user);

        postComment.putComment(content);
        postCommentRepository.save(postComment);
        return "댓글 수정을 성공하였습니다.";
    }

    // 게시글 댓글 삭제
    public String deleteComment(Long commentId, User user) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.")
        );

        // 댓글 작성자 여부
        User writer = postComment.getUser();
        getAuthority(writer, user);

        postCommentRepository.deleteById(commentId);
        return "댓글 삭제를 성공하였습니다.";
    }

    // 작성자 일치 여부
    public void getAuthority(User writer, User user) {
        if(!writer.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글에 대한 권한이 없습니다.");
        }
    }

    // 빈 값 금지
    public void noBlank(String comment) {
        if (comment.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글을 입력해주세요.");
        }
    }


}
