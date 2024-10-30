CREATE TABLE comment (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    post_id UUID NOT NULL,
    comment_id UUID,
    comment_status_id BIGINT,
    content TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    published_at TIMESTAMP,
    is_solution BOOLEAN,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE,
    CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
