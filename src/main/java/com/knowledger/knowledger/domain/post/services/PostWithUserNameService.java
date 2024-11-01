package com.knowledger.knowledger.domain.post.services;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostWithUserNameService implements IPostWithUserNameService {
    private final IUserRepository userRepository;

    public PostWithUserNameService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Post enrichWithUserName(Post post) {
        userRepository.findById(post.getUserId())
                .ifPresent(u -> post.setUserName(u.getName()));
        return post;
    }
}
