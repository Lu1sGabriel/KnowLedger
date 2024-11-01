package com.knowledger.knowledger.application.usecases.post;

import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.controller.post.PostRegisterDTO;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;

@Component
public class PostRegister {

    private final IPostGateway _IPostGateway;
    private final IMapperDTO<PostDetailDTO, Post> _MapperDTO;

    public PostRegister(IPostGateway iPostGateway, IMapperDTO<PostDetailDTO, Post> iMapper) {
        _IPostGateway = iPostGateway;
        _MapperDTO = iMapper;
    }

    public PostDetailDTO apply(PostRegisterDTO dto) {
        var post = _IPostGateway.register(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getPostTypeId());
        return _MapperDTO.toDto(post);
    }
}
