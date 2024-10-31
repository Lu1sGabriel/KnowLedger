package com.knowledger.knowledger.application.usecases.post;

import java.util.UUID;
import java.util.List;
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

    public List<PostDetailDTO> apply(UUID userId) {
        var post = _IPostGateway.getAllByUserId(userId);
        return _MapperDTO.toDtoList(post);
    }
}
