package com.knowledger.knowledger.domain.comment.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.persistence.post.PostProjection;

public interface ICommentService {

    Map<UUID, List<Comment>> fetchComments(Page<PostProjection> postProjections);

}
