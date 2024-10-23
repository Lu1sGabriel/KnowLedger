CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       role_id INTEGER NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP,
                       deleted_at TIMESTAMP,
                       is_active BOOLEAN NOT NULL,

                       CONSTRAINT fk_role
                           FOREIGN KEY(role_id)
                               REFERENCES role(id)
);
