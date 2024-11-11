package com.knowledger.knowledger.domain.comment.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.persistence.comment.ICommentRepository;
import com.knowledger.knowledger.infra.persistence.post.PostProjection;
import com.knowledger.knowledger.infra.persistence.post.PostProjection.CommentProjection;

@Service
public class CommentService implements ICommentService {

    private final ICommentRepository commentRepository;
    private final IMapper<CommentProjection, Comment> commentMapper;

    public CommentService(ICommentRepository commentRepository, IMapper<CommentProjection, Comment> commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public Map<UUID, List<Comment>> fetchComments(Page<PostProjection> postProjections) {
        List<UUID> postIds = postProjections.stream()
                .map(PostProjection::getPostId)
                .collect(Collectors.toList());

        return commentRepository.findAllByPostIds(postIds).stream()
                .map(commentMapper::toDomain)
                .collect(Collectors.groupingBy(comment -> comment.getPost().getId()));
    }
}
