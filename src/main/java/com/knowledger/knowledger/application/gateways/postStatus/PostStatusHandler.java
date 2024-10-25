package com.knowledger.knowledger.application.gateways.postStatus;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.postStatus.PostStatus;
import com.knowledger.knowledger.infra.gateways.postType.IPostStatusGateway;
import com.knowledger.knowledger.infra.persistence.postStatus.IPostStatusRepository;
import com.knowledger.knowledger.infra.persistence.postStatus.PostStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostStatusHandler implements IPostStatusGateway {

    private final IPostStatusRepository _iPostStatusRepository;
    private final IMapper<PostStatusEntity, PostStatus> _iMapper;

    public PostStatusHandler(IPostStatusRepository iPostStatusRepository, IMapper<PostStatusEntity, PostStatus> iMapper) {
        _iPostStatusRepository = iPostStatusRepository;
        _iMapper = iMapper;
    }

    @Override
    public List<PostStatus> getAll() {
        var postStatusEntities = _iPostStatusRepository.findAll();
        return _iMapper.toDomainList(postStatusEntities);
    }

}