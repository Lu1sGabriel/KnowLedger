package com.knowledger.knowledger.infra.persistence.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.knowledger.knowledger.infra.validations.annotations.Email;
import com.knowledger.knowledger.infra.validations.annotations.Name;
import com.knowledger.knowledger.infra.validations.annotations.Password;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    @Setter(value = AccessLevel.NONE)
    private UUID id;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "name", nullable = false)
    @Name
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Password
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public UserEntity(Long roleId, String name, String email, String password, LocalDateTime createdAt,
            boolean isActive) {
        this.roleId = roleId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

}