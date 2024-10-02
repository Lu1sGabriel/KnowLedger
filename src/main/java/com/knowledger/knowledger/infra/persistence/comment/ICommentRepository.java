package com.knowledger.knowledger.infra.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, UUID> {
}
