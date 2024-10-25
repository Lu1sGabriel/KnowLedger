package com.knowledger.knowledger.application.usecases.postStatus;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.postStatus.PostStatus;
import com.knowledger.knowledger.infra.controller.postStatus.PostStatusDetailDTO;
import com.knowledger.knowledger.infra.gateways.postType.IPostStatusGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostStatusGetAll {

    private final IPostStatusGateway _iPostStatusGateway;
    private final IMapperDTO<PostStatusDetailDTO, PostStatus> _iMapperDto;

    public PostStatusGetAll(IPostStatusGateway iPostStatusGateway, IMapperDTO<PostStatusDetailDTO, PostStatus> iMapperDto) {
        _iPostStatusGateway = iPostStatusGateway;
        _iMapperDto = iMapperDto;
    }

    public List<PostStatusDetailDTO> getAll() {
        var postStatus = _iPostStatusGateway.getAll();
        return _iMapperDto.toDtoList(postStatus);
    }

}