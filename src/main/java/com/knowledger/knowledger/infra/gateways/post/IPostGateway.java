package com.knowledger.knowledger.infra.gateways.post;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.knowledger.knowledger.domain.post.Post;

public interface IPostGateway {

    Post register(UUID userId, String title, String content, Long postTypeId, Long departmentId);

    Page<Post> getAll(Pageable pageable);

    Post getById(UUID id);

    Page<Post> getAllByUserId(UUID userId, Pageable pageable);
}
