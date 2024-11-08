package com.knowledger.knowledger.domain.post.factories;

import java.util.UUID;

import com.knowledger.knowledger.domain.post.Post;

public interface IPostFactory {

    Post create(UUID userId, Long postTypeId, String title, String content);

}
