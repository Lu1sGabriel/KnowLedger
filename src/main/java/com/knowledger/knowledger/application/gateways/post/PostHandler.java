package com.knowledger.knowledger.application.gateways.post;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.factories.IPostFactory;
import com.knowledger.knowledger.domain.post.services.IPostValidationService;
import com.knowledger.knowledger.domain.post.services.IPostWithUserNameService;
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
    private final IPostWithUserNameService _IPostWithUserNameService;

    public PostHandler(IMapper<PostEntity, Post> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository, IPostFactory iPostFactory, IPostValidationService postValidationService,
            IPostWithUserNameService postWithUserNameService) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IPostFactory = iPostFactory;
        _IPostValidationService = postValidationService;
        _IPostWithUserNameService = postWithUserNameService;
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
        var posts = _iPostRepository.findAllByDeletedAtIsNull(Constants.PostStatus.APPROVED);
        return _iMapper.toDomainList(posts)
                .stream()
                .map(_IPostWithUserNameService::enrichWithUserName)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAllByUserId(UUID userId) {
        _IPostValidationService.validateUserExists(userId);
        var posts = _iPostRepository.findAllByUserIdAndDeletedAtIsNull(userId, Constants.PostStatus.APPROVED);
        return _iMapper.toDomainList(posts)
                .stream()
                .map(_IPostWithUserNameService::enrichWithUserName)
                .collect(Collectors.toList());
    }
}