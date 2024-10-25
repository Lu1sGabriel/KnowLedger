package com.knowledger.knowledger.application.gateways.postType;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.postType.PostType;
import com.knowledger.knowledger.infra.gateways.postType.IPostTypeGateway;
import com.knowledger.knowledger.infra.persistence.postType.IPostTypeRepository;
import com.knowledger.knowledger.infra.persistence.postType.PostTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypeHandler implements IPostTypeGateway {

    private final IPostTypeRepository _iPostTypeRepository;
    private final IMapper<PostTypeEntity, PostType> _iMapper;

    public PostTypeHandler(IPostTypeRepository iPostTypeRepository, IMapper<PostTypeEntity, PostType> iMapper) {
        _iPostTypeRepository = iPostTypeRepository;
        _iMapper = iMapper;
    }

    @Override
    public List<PostType> getAll() {
        var postTypeEntities = _iPostTypeRepository.findAll();
        return _iMapper.toDomainList(postTypeEntities);
    }

}