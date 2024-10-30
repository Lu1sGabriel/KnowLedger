CREATE TABLE post (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    post_type_id BIGINT,
    post_status_id BIGINT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    published_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE
);
