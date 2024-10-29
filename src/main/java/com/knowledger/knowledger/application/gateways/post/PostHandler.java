package com.knowledger.knowledger.application.gateways.post;

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
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostHandler implements IPostGateway {

    private final IMapper<PostEntity, Post> _iMapper;
    private final IPostRepository _iPostRepository;
    private final IPostFactory _IPostFactory;
    private final IUserRepository _IUserRepository;

    public PostHandler(IMapper<PostEntity, Post> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository, IPostFactory iPostFactory) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IUserRepository = iUserRepository;
        _IPostFactory = iPostFactory;
    }

    @Override
    public Post register(UUID userId, String title, String content, Long postTypeId, Long postStatusId) {

        var user = _IUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usúario não encontrado!", HttpStatus.NOT_FOUND));

        var post = _IPostFactory.create(user, postTypeId, postStatusId, title, content);

        _iPostRepository.save(_iMapper.toEntity(post));

        return post;
    }
}