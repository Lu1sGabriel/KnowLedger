package com.knowledger.knowledger.application.usecases.post;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;

@Component
public class PostGetAllByUserId {
    private final IPostGateway _IPostGateway;
    private final IMapperDTO<PostDetailDTO, Post> _MapperDTO;

    public PostGetAllByUserId(IPostGateway iPostGateway, IMapperDTO<PostDetailDTO, Post> iMapper) {
        _IPostGateway = iPostGateway;
        _MapperDTO = iMapper;
    }

    public Page<PostDetailDTO> apply(UUID userId, Pageable pageable) {
        var post = _IPostGateway.getAllByUserId(userId, pageable);
        return _MapperDTO.toDtoPage(post);
    }
}
