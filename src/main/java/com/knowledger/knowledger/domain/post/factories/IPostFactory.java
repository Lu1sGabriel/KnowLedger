package com.knowledger.knowledger.domain.post.factories;

import com.knowledger.knowledger.domain.post.Post;

import java.util.UUID;

public interface IPostFactory {

    Post create(UUID userId, Long postTypeId, Long departmentId, String title, String content);

}