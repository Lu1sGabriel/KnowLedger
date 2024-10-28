package com.knowledger.knowledger.infra.gateways.post;

import java.util.UUID;

import com.knowledger.knowledger.domain.post.Post;

public interface IPostGateway {

    Post register(UUID userId, String title, String content, Long postTypeId, Long postStatusId);
}
