package com.knowledger.knowledger.application.gateways.post;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostHandler implements IPostGateway {

    private final IMapper<PostEntity, Post> _iMapper;
    private final IPostRepository _iPostRepository;
    private final IUserRepository _IUserRepository;

    public PostHandler(IMapper<PostEntity, Post> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IUserRepository = iUserRepository;
    }

    @Override
    public Post register(UUID userId, String title, String content, Long postTypeId, Long postStatusId) {

        _IUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usúario não encontrado!", HttpStatus.NOT_FOUND));

        var post = new Post();

        _iPostRepository.save(_iMapper.toEntity(post));
        return post;
    }

}
