package com.knowledger.knowledger.domain.post.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.knowledger.knowledger.domain.post.Post;

public interface IPostService {
    Post registerPost(UUID userId, String title, String content, Long postTypeId, Long departmentId);

    Page<Post> getAllPosts(Pageable pageable);

    Page<Post> getPostsByUserId(UUID userId, Pageable pageable);
}
