package com.knowledger.knowledger.infra.persistence.user;

import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id AND u.deletedAt IS NULL")
    Optional<UserEntity> findById(@Param("id") UUID id);
}
