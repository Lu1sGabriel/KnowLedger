ALTER TABLE post
    ADD COLUMN department_id BIGINT,
    ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE;
