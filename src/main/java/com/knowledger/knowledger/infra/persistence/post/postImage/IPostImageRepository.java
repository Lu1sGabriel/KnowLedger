package com.knowledger.knowledger.infra.persistence.post.postImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPostImageRepository extends JpaRepository<PostImageEntity, UUID> {
    @Query("SELECT pe FROM PostEntity pe WHERE  pe.id =:uuid  AND pe.deletedAt IS NULL")
    Optional<PostImageEntity> findByPostId(UUID uuid);
}