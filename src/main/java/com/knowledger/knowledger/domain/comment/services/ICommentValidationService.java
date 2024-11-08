package com.knowledger.knowledger.domain.comment.services;

import java.util.UUID;

public interface ICommentValidationService {
    void validateUserExists(UUID userId);

    void validatePostExists(UUID postId);

    void validateCommentExists(UUID commentId);

    void validateCommentBelongsToPost(UUID postId, UUID commentId);
}
