package com.knowledger.knowledger.infra.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, UUID> {
    List<CommentEntity> findAllByPostIdAndDeletedAtIsNull(UUID postId);

    List<CommentEntity> findAllByDeletedAtIsNull();
}
