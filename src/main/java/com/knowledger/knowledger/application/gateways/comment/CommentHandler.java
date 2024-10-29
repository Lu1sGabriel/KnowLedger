package com.knowledger.knowledger.application.gateways.comment;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.comment.ICommentGateway;
import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.comment.ICommentRepository;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class CommentHandler implements ICommentGateway {

    private final IMapper<CommentEntity, Comment> _iMapper;
    private final IPostRepository _iPostRepository;
    private final IUserRepository _IUserRepository;
    private final ICommentRepository _ICommentRepository;

    public CommentHandler(IMapper<CommentEntity, Comment> iMapper, IPostRepository iPostRepository,
            IUserRepository iUserRepository, ICommentRepository iCommentRepository) {
        _iMapper = iMapper;
        _iPostRepository = iPostRepository;
        _IUserRepository = iUserRepository;
        _ICommentRepository = iCommentRepository;
    }

    @Override
    public Comment register(UUID userId, UUID postId, String content, UUID commentId, Long commentStatusId) {

        _IUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usúario não encontrado!", HttpStatus.NOT_FOUND));

        _iPostRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Post não encontrado!", HttpStatus.NOT_FOUND));

        var comment = new Comment();

        _ICommentRepository.save(_iMapper.toEntity(comment));

        return comment;
    }

}