package com.knowledger.knowledger.infra.persistence.postType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostTypeRepository extends JpaRepository<PostTypeEntity, Long> {
}
