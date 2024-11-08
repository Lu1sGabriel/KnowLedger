package com.knowledger.knowledger.infra.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<PostEntity, UUID> {


        @Query("SELECT p.id AS postId, p.title AS title, p.content AS content, p.createdAt AS createdAt, " +
                        "p.updatedAt AS updatedAt, u.name AS postUserName " +
                        "FROM PostEntity p " +
                        "JOIN UserEntity u ON p.userId = u.id " +
                        "WHERE p.postStatusId = :approvedStatusId " +
                        "AND p.userId = :userId " +
                        "AND p.deletedAt IS NULL")
        Page<PostProjection> findAllByUserId(UUID userId, Long approvedStatusId, Pageable pageable);

        @Query("SELECT " +
                        "p.id AS postId, " +
                        "p.title AS title, " +
                        "p.content AS content, " +
                        "p.createdAt AS createdAt, " +
                        "p.updatedAt AS updatedAt, " +
                        "p.deletedAt AS deletedAt, " +
                        "p.publishedAt AS publishedAt, " +
                        "p.postTypeId AS postTypeId, " +
                        "p.postStatusId AS postStatusId, " +
                        "u.id AS postUserId, " +
                        "u.name AS postUserName " +
                        "FROM PostEntity p " +
                        "JOIN UserEntity u ON p.userId = u.id " +
                        "WHERE p.postStatusId = :approvedStatusId " +
                        "AND p.deletedAt IS NULL")
        Page<PostProjection> findAll(Long approvedStatusId, Pageable pageable);

}