package com.knowledger.knowledger.infra.persistence.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends JpaRepository<TagEntity, Long> {
}
