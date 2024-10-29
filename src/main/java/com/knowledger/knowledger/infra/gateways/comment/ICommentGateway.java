package com.knowledger.knowledger.infra.gateways.comment;

import java.util.UUID;

import com.knowledger.knowledger.domain.comment.Comment;

public interface ICommentGateway {

    Comment register(UUID userId, UUID postId, String content, UUID commentId, Long commentStatusId);

}