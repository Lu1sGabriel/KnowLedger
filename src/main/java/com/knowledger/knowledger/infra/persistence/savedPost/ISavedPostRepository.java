package com.knowledger.knowledger.infra.persistence.savedPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISavedPostRepository extends JpaRepository<SavedPostEntity, UUID> {
}
