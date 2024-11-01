package com.knowledger.knowledger.domain.post.factories;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

@Service
public class PostFactory implements IPostFactory {

    @Override
    public Post create(UserEntity user, Long postTypeId, String title, String content) {
        return new Post(user, postTypeId, title, content);
    }

}
