package com.knowledger.knowledger.infra.gateways.postType;

import com.knowledger.knowledger.domain.postStatus.PostStatus;

import java.util.List;

public interface IPostStatusGateway {
    List<PostStatus> getAll();
}
