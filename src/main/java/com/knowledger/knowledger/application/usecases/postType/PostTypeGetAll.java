package com.knowledger.knowledger.application.usecases.postType;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.postType.PostType;
import com.knowledger.knowledger.infra.controller.postType.PostTypeDetailDTO;
import com.knowledger.knowledger.infra.gateways.postType.IPostTypeGateway;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostTypeGetAll {
    private final IPostTypeGateway _iPostTypeGateway;
    private final IMapperDTO<PostTypeDetailDTO, PostType> _iMapperDTO;

    public PostTypeGetAll(IPostTypeGateway iPostTypeGateway, IMapperDTO<PostTypeDetailDTO, PostType> iMapperDTO) {
        _iPostTypeGateway = iPostTypeGateway;
        _iMapperDTO = iMapperDTO;
    }

    public List<PostTypeDetailDTO> getAll(){
        var postTypes = _iPostTypeGateway.getAll();
        return _iMapperDTO.toDtoList(postTypes);
    }
}
