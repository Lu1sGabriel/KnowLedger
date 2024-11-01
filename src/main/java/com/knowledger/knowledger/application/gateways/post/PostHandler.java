package com.knowledger.knowledger.application.gateways.post;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.factories.IPostFactory;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.postType.IPostTypeRepository;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostHandler implements IPostGateway {

    private final IMapper<PostEntity, Post> _iMapper;
    private final IPostRepository _iPostRepository;
    private final IPostFactory _IPostFactory;
    private final IPostTypeRepository _IPostTypeRepository;
    private final IUserRepository _IUserRepository;

    public PostHandler(IMapper<PostEntity, Post> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository, IPostFactory iPostFactory, IPostTypeRepository iPostTypeRepository) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IUserRepository = iUserRepository;
        _IPostFactory = iPostFactory;
        _IPostTypeRepository = iPostTypeRepository;
    }

    @Override
    public Post register(UUID userId, String title, String content, Long postTypeId) {

        var user = _IUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usúario não encontrado!", HttpStatus.NOT_FOUND));

        _IPostTypeRepository.findById(postTypeId)
                .orElseThrow(() -> new BusinessException("Tipo de Post não encontrado!", HttpStatus.NOT_FOUND));

        var post = _IPostFactory.create(user, postTypeId, title, content);

        _iPostRepository.save(_iMapper.toEntity(post));

        return post;
    }

    @Override
    public List<Post> getAll() {

        var posts = _iPostRepository.findAll();

        return _iMapper.toDomainList(posts);
    }

    @Override
    public List<Post> getAllByUserId(UUID userId) {

        _IUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usúario não encontrado!", HttpStatus.NOT_FOUND));

        var posts = _iPostRepository.findAllByUserId(userId);

        return _iMapper.toDomainList(posts);
    }
}