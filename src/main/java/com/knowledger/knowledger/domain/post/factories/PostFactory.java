package com.knowledger.knowledger.domain.post.factories;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

@Service
public class PostFactory implements IPostFactory {

    // private final IPostRepository _IPostRepository;

    // public PostFactory(IPostRepository _IPostRepository) {
    // this._IPostRepository = _IPostRepository;
    // }

    @Override
    public Post create(UserEntity user, Long postTypeId, Long postStatusId, String title, String content) {
        return new Post(user, postTypeId, postStatusId, title, content);
    }

}
