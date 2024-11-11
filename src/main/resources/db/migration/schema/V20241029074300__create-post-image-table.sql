CREATE TABLE post_image
(
    id         UUID PRIMARY KEY,
    post_id    UUID         NOT NULL,
    path       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);
