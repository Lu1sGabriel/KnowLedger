package com.knowledger.knowledger.domain.post.services;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.comment.services.ICommentService;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.factories.IPostFactory;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.post.PostProjection;

@Service
public class PostService implements IPostService {

    private final IMapper<PostProjection, Post> postProjectionMapper;
    private final IMapper<PostEntity, Post> postEntityMapper;
    private final IPostRepository postRepository;
    private final IPostFactory postFactory;
    private final PostValidationService validationService;
    private final ICommentService commentService;

    public PostService(
            IMapper<PostProjection, Post> postProjectionMapper,
            IMapper<PostEntity, Post> postEntityMapper,
            IPostRepository postRepository,
            IPostFactory postFactory,
            PostValidationService validationService,
            ICommentService commentService) {

        this.postProjectionMapper = postProjectionMapper;
        this.postEntityMapper = postEntityMapper;
        this.postRepository = postRepository;
        this.postFactory = postFactory;
        this.validationService = validationService;
        this.commentService = commentService;
    }

    public Post registerPost(UUID userId, String title, String content, Long postTypeId) {
        validationService.validateUserExists(userId);
        validationService.validatePostTypeExists(postTypeId);
        Post post = postFactory.create(userId, postTypeId, title, content);
        postRepository.save(postEntityMapper.toEntity(post));
        return post;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        Page<PostProjection> postProjections = postRepository.findAll(Constants.PostStatus.APPROVED, pageable);
        return mapPostsWithComments(postProjections);
    }

    public Page<Post> getPostsByUserId(UUID userId, Pageable pageable) {
        validationService.validateUserExists(userId);
        Page<PostProjection> postProjections = postRepository.findAllByUserId(userId, Constants.PostStatus.APPROVED,
                pageable);
        return mapPostsWithComments(postProjections);
    }

    private Page<Post> mapPostsWithComments(Page<PostProjection> postProjections) {
        var commentsMap = commentService.fetchComments(postProjections);
        return postProjections.map(projection -> {
            Post post = postProjectionMapper.toDomain(projection);
            post.setComments(commentsMap.getOrDefault(post.getId(), new ArrayList<>()));
            return post;
        });
    }
}
