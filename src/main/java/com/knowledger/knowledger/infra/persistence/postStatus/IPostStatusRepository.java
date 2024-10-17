package com.knowledger.knowledger.infra.persistence.postStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostStatusRepository extends JpaRepository<PostStatusEntity, Long> {
}
