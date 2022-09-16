package com.example.harmony.domain.community.service;

import com.example.harmony.domain.community.model.Like;
import com.example.harmony.domain.community.model.Post;
import com.example.harmony.domain.community.repository.LikeRepository;
import com.example.harmony.domain.community.repository.PostRepository;
import com.example.harmony.domain.notification.model.NotificationRequest;
import com.example.harmony.domain.notification.service.NotificationService;
import com.example.harmony.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    private final NotificationService notificationService;

    // 게시글 좋아요 + 이미 누른 사람
    public String doLike(Long postId, User user,boolean like) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"게시글이 존재하지 않습니다.")
        );

        // 이미 누른 사람
        if(likeRepository.findByPostAndUser(post, user).isPresent()) {
            Like isPresent = likeRepository.findByPostAndUser(post,user).orElseThrow(
                    ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자가 이 게시글에 좋아요를 누르지 않았습니다.")
            );
            isPresent.putLike(like);
            likeRepository.save(isPresent);
        } else {
            likeRepository.save(new Like(like, post, user));
        }

        notificationService.createNotification(new NotificationRequest("like", "create"), Collections.singletonList(post.getUser()));
        return "좋아요를 눌렀습니다.";
    }

    // 게시글 좋아요 취소
    @Transactional
    public String undoLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"게시글이 존재하지 않습니다.")
        );
        likeRepository.deleteLikeByPostAndUser(post, user);
        return "좋아요를 취소하였습니다.";
    }
}
