package com.knowledger.knowledger.infra.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.knowledger.knowledger.infra.persistence.post.PostProjection.CommentProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, UUID> {

    List<CommentEntity> findAllByPostIdAndDeletedAtIsNull(UUID postId);

    List<CommentEntity> findAllByDeletedAtIsNull();

    @Query("SELECT " +
            "c.postId AS postId, " +
            "c.id AS id, " +
            "c.commentId AS commentId, " +
            "c.content AS content, " +
            "c.createdAt AS createdAt, " +
            "c.updatedAt AS updatedAt, " +
            "c.deletedAt AS deletedAt, " +
            "c.publishedAt AS publishedAt, " +
            "cu.id AS userId, " +
            "cu.name AS userName " +
            "FROM CommentEntity c " +
            "JOIN UserEntity cu ON c.userId = cu.id " +
            "WHERE c.postId IN :postIds")
    List<CommentProjection> findAllByPostIds(@Param("postIds") List<UUID> postIds);

}
