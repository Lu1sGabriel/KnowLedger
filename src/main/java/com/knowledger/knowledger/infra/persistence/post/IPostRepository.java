package com.knowledger.knowledger.infra.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<PostEntity, UUID> {
}
