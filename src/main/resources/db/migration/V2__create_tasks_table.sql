CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES users(id)
);