package com.knowledger.knowledger.application.gateways.comment;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.domain.comment.services.ICommentValidationService;
import com.knowledger.knowledger.infra.gateways.comment.ICommentGateway;
import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.comment.ICommentRepository;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class CommentHandler implements ICommentGateway {

        private final IMapper<CommentEntity, Comment> _iMapper;
        private final ICommentRepository _ICommentRepository;
        private final ICommentValidationService _IValidationService;

        public CommentHandler(IMapper<CommentEntity, Comment> iMapper, IPostRepository iPostRepository,
                        IUserRepository iUserRepository, ICommentRepository iCommentRepository,
                        ICommentValidationService validationService) {
                _iMapper = iMapper;
                _ICommentRepository = iCommentRepository;
                _IValidationService = validationService;
        }

        @Override
        public Comment register(UUID userId, UUID postId, String content, UUID commentId) {

                _IValidationService.validateUserExists(userId);
                _IValidationService.validatePostExists(postId);
                _IValidationService.validateCommentExists(commentId);
                _IValidationService.validateCommentBelongsToPost(postId, commentId);

                var comment = new Comment(userId, postId, commentId, content);

                _ICommentRepository.save(_iMapper.toEntity(comment));

                return comment;
        }

        @Override
        public List<Comment> getAllByPostId(UUID postId) {

                _IValidationService.validatePostExists(postId);

                var commentEntities = _ICommentRepository.findAllByPostIdAndDeletedAtIsNull(postId);

                return _iMapper.toDomainList(commentEntities);
        }

}