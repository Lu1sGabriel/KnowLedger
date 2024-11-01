package com.knowledger.knowledger.domain.comment.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.knowledger.knowledger.infra.persistence.comment.ICommentRepository;
import com.knowledger.knowledger.infra.persistence.post.IPostRepository;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import com.knowledger.knowledger.infra.exceptions.BusinessException;

@Service
public class CommentValidationService implements ICommentValidationService {

    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;

    public CommentValidationService(IUserRepository userRepository, IPostRepository postRepository,
            ICommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void validateUserExists(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validatePostExists(UUID postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Post não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validateCommentExists(UUID commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("Comentário não encontrado!", HttpStatus.NOT_FOUND));
    }

    public void validateCommentBelongsToPost(UUID postId, UUID commentId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Post não encontrado!", HttpStatus.NOT_FOUND));

        boolean belongsToPost = post.getComments().stream()
                .anyMatch(c -> c.getCommentId().equals(commentId));

        if (!belongsToPost) {
            throw new BusinessException("Comentário não pertence ao post especificado!", HttpStatus.BAD_REQUEST);
        }
    }
}