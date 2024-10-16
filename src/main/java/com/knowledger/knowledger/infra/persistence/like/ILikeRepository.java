package com.knowledger.knowledger.infra.persistence.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILikeRepository extends JpaRepository<LikeEntity, UUID> {
}
