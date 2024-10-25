package com.knowledger.knowledger.infra.persistence.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
