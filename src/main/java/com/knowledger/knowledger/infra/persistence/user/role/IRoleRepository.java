package com.knowledger.knowledger.infra.persistence.user.role;

import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
}
