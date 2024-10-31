package com.knowledger.knowledger.application.usecases.post;

import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.gateways.post.IPostGateway;
import java.util.List;

@Component
public class PostGetAll {
    private final IPostGateway _IPostGateway;
    private final IMapperDTO<PostDetailDTO, Post> _MapperDTO;

    public PostGetAll(IPostGateway iPostGateway, IMapperDTO<PostDetailDTO, Post> iMapper) {
        _IPostGateway = iPostGateway;
        _MapperDTO = iMapper;
    }

    public List<PostDetailDTO> apply() {
        var post = _IPostGateway.getAll();
        return _MapperDTO.toDtoList(post);
    }
}
