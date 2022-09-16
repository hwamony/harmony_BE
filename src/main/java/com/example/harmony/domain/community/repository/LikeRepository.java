package com.example.harmony.domain.community.repository;

import com.example.harmony.domain.community.model.Like;
import com.example.harmony.domain.community.model.Post;
import com.example.harmony.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);
    void deleteLikeByPostAndUser(Post post, User user);
    boolean existsByUser(User user);
    void deleteAllByUser(User user);

}