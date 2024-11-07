package com.knowledger.knowledger.application.usecases.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;

@Component
public class PostGetAll {
    private final IPostGateway _IPostGateway;
    private final IMapperDTO<PostDetailDTO, Post> _MapperDTO;

    public PostGetAll(IPostGateway iPostGateway, IMapperDTO<PostDetailDTO, Post> iMapper) {
        _IPostGateway = iPostGateway;
        _MapperDTO = iMapper;
    }

    public Page<PostDetailDTO> apply(Pageable pageable) {
        var post = _IPostGateway.getAll(pageable);
        return _MapperDTO.toDtoPage(post);
    }
}
