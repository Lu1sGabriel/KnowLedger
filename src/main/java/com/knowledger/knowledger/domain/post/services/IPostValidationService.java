package com.knowledger.knowledger.domain.post.services;

import java.util.UUID;

public interface IPostValidationService {
    void validateUserExists(UUID userId);

    void validatePostExists(UUID postId);

    void validatePostTypeExists(Long postId);

    void validateCommentExists(UUID commentId);

}
