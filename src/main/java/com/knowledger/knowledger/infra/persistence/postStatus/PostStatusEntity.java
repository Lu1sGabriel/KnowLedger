package com.knowledger.knowledger.infra.persistence.postStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_status")
@Getter
@Setter
@NoArgsConstructor
public class PostStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", length = 300)
    private String description;

    public PostStatusEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

}