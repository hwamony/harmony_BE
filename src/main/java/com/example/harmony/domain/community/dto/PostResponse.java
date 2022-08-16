package com.example.harmony.domain.community.dto;

import com.example.harmony.domain.community.entity.Post;
import com.example.harmony.domain.community.entity.Tag;
import com.example.harmony.domain.user.entity.Family;
import com.example.harmony.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private List<String> tags;

    private String imageUrl;

    private Map<String,Object> poster;

    private boolean isPoster;

    private LocalDateTime createdAt;

    private List<PostCommentResponse> comments;

    private int commentCount;

    private int likeCount;

    private boolean like;

    public PostResponse(Post post, boolean isPoster, List<PostCommentResponse> comments, boolean like) {
      this.title = post.getTitle();
      this.content = post.getContent();
      this.tags = post.getTags().stream()
              .map(Tag::getTag)
              .collect(Collectors.toList());
      this.imageUrl = post.getImageUrl();
      this.poster= userInfo(post.getUser(),post.getUser().getFamily());
      this.isPoster = isPoster;
      this.createdAt = post.getCreatedAt();
      this.comments = comments;
      this.likeCount = post.getLikes().size();
      this.like = like;
    }

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = post.getTags().stream()
                .map(Tag::getTag)
                .collect(Collectors.toList());
        this.imageUrl = post.getImageUrl();
        this.poster = userInfo(post.getUser(),post.getUser().getFamily());
        this.createdAt = post.getCreatedAt();
        this.commentCount = post.getComments().size();
        this.likeCount = post.getLikes().size();
    }

    public Map<String,Object> userInfo(User user, Family family) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("level",family.getLevel());
        userInfo.put("flower", family.isFlower());
        userInfo.put("nickname", user.getNickname());
        return userInfo;
    }
}