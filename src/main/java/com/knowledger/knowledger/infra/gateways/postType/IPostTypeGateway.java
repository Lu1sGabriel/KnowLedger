package com.knowledger.knowledger.infra.gateways.postType;

import com.knowledger.knowledger.domain.postType.PostType;

import java.util.List;

public interface IPostTypeGateway {
    List<PostType> getAll();
}
