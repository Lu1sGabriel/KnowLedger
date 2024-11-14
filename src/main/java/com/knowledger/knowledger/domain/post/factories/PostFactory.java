package com.knowledger.knowledger.domain.post.factories;

import com.knowledger.knowledger.domain.post.Post;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostFactory implements IPostFactory {

    @Override
    public Post create(UUID userId, Long postTypeId, Long departmentId, String title, String content) {
        return new Post(userId, postTypeId, departmentId, title, content);
    }

}
