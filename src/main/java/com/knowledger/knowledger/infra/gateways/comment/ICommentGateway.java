package com.knowledger.knowledger.infra.gateways.comment;

import java.util.List;
import java.util.UUID;

import com.knowledger.knowledger.domain.comment.Comment;

public interface ICommentGateway {

    Comment register(UUID userId, UUID postId, String content, UUID commentId, Long commentStatusId);

    List<Comment> getAllByPostId(UUID PostId);

}