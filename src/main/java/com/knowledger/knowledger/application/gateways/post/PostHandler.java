package com.knowledger.knowledger.application.gateways.post;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.factories.IPostFactory;
import com.knowledger.knowledger.domain.post.services.IPostValidationService;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostHandler implements IPostGateway {

    private final IMapper<PostEntity, Post> _iMapper;
    private final IPostRepository _iPostRepository;
    private final IPostFactory _IPostFactory;
    private final IPostValidationService _IPostValidationService;

    public PostHandler(IMapper<PostEntity, Post> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository, IPostFactory iPostFactory, IPostValidationService postValidationService) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IPostFactory = iPostFactory;
        _IPostValidationService = postValidationService;
    }

    @Override
    public Post register(UUID userId, String title, String content, Long postTypeId) {

        _IPostValidationService.validateUserExists(userId);
        _IPostValidationService.validatePostTypeExists(postTypeId);

        var post = _IPostFactory.create(userId, postTypeId, title, content);

        _iPostRepository.save(_iMapper.toEntity(post));

        return post;
    }

    @Override
    public List<Post> getAll() {

        var posts = _iPostRepository.findAllByDeletedAtIsNull();

        return _iMapper.toDomainList(posts);
    }

    @Override
    public List<Post> getAllByUserId(UUID userId) {

        _IPostValidationService.validateUserExists(userId);

        var posts = _iPostRepository.findAllByUserIdAndDeletedAtIsNull(userId);

        return _iMapper.toDomainList(posts);
    }
}