package com.knowledger.knowledger.application.gateways.post;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.services.IPostService;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;

@Service
public class PostHandler implements IPostGateway {

    private final IPostService _IPostService;

    public PostHandler(IPostService postService) {
        _IPostService = postService;
    }

    @Override
    public Post register(UUID userId, String title, String content, Long postTypeId, Long departmentId) {
        return _IPostService.registerPost(userId, title, content, postTypeId, departmentId);
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return _IPostService.getAllPosts(pageable);
    }

    @Override
    public Page<Post> getAllByUserId(UUID userId, Pageable pageable) {
        return _IPostService.getPostsByUserId(userId, pageable);
    }

    @Override
    public Post getById(UUID id) {
        return _IPostService.getById(id);
    }

}