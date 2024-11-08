package com.knowledger.knowledger.domain.post.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.comment.ICommentRepository;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.postType.IPostTypeRepository;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

@Service
public class PostValidationService implements IPostValidationService {

    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;
    private final IPostTypeRepository _IPostTypeRepository;

    public PostValidationService(IUserRepository userRepository, IPostRepository postRepository,
            ICommentRepository commentRepository, IPostTypeRepository postTypeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this._IPostTypeRepository = postTypeRepository;
    }

    public void validateUserExists(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validatePostExists(UUID postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Post não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validatePostTypeExists(Long postTypeId) {
        _IPostTypeRepository.findById(postTypeId)
                .orElseThrow(() -> new BusinessException("Tipo de Post não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validateCommentExists(UUID commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("Comentário não encontrado!", HttpStatus.NOT_FOUND));
    }
}