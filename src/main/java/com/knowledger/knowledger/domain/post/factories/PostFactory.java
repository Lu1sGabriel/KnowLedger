package com.knowledger.knowledger.domain.post.factories;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.domain.post.Post;

@Service
public class PostFactory implements IPostFactory {

    @Override
    public Post create(UUID userId, Long postTypeId, String title, String content) {
        return new Post(userId, postTypeId, title, content);
    }

}
