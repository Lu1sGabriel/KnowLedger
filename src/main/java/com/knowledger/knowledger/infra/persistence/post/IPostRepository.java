package com.knowledger.knowledger.infra.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("SELECT p FROM PostEntity p WHERE p.userId = :userId AND p.postStatusId = :approvedStatusId AND p.deletedAt IS NULL")
    List<PostEntity> findAllByUserIdAndDeletedAtIsNull(UUID userId, Long approvedStatusId);

    @Query("SELECT p FROM PostEntity p WHERE p.postStatusId = :approvedStatusId AND p.deletedAt IS NULL")
    List<PostEntity> findAllByDeletedAtIsNull(Long approvedStatusId);

}
