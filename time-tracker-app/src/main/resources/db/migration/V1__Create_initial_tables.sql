-- Migration V1__Create_tables.sql

-- Таблица проектов
CREATE TABLE projects (
                          id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(500),
                          status VARCHAR(255) NOT NULL
                              CONSTRAINT projects_status_check
                                  CHECK (status IN ('ACTIVE', 'COMPLETED', 'ARCHIVED')),
                          created_at TIMESTAMP(6) NOT NULL,
                          updated_at TIMESTAMP(6)
);

-- Таблица задач
CREATE TABLE tasks (
                       id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description VARCHAR(500),
                       estimated BIGINT,
                       status VARCHAR(255) NOT NULL
                           CONSTRAINT tasks_status_check
                               CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELED')),
                       project_id BIGINT REFERENCES projects(id),
                       created_at TIMESTAMP(6) NOT NULL,
                       updated_at TIMESTAMP(6)
);

-- Таблица пользователей
CREATE TABLE users (
                       id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(320) NOT NULL UNIQUE,
                       password VARCHAR(1000) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL
                           CONSTRAINT users_role_check
                               CHECK (role IN ('ROLE_ADMIN', 'ROLE_USER')),
                       created_at TIMESTAMP(6) NOT NULL,
                       updated_at TIMESTAMP(6)
);

-- Таблица назначений проектов пользователям
CREATE TABLE project_assignments (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     project_id BIGINT NOT NULL REFERENCES projects(id),
                                     user_id BIGINT NOT NULL REFERENCES users(id),
                                     role VARCHAR(255) NOT NULL,
                                     assigned_at TIMESTAMP(6) NOT NULL,
                                     created_at TIMESTAMP(6) NOT NULL,
                                     updated_at TIMESTAMP(6)
);

-- Таблица учёта времени (записи)
CREATE TABLE records (
                         id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         task_id BIGINT REFERENCES tasks(id),
                         user_id BIGINT REFERENCES users(id),
                         title VARCHAR(100) NOT NULL,
                         description VARCHAR(500),
                         start_time TIMESTAMP(6) NOT NULL,
                         end_time TIMESTAMP(6) NOT NULL,
                         created_at TIMESTAMP(6) NOT NULL,
                         updated_at TIMESTAMP(6)
);

-- Таблица назначений задач пользователям
CREATE TABLE task_assignments (
                                  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                  task_id BIGINT REFERENCES tasks(id),
                                  user_id BIGINT REFERENCES users(id),
                                  assigned_at TIMESTAMP(6) NOT NULL,
                                  created_at TIMESTAMP(6) NOT NULL,
                                  updated_at TIMESTAMP(6)
);

-- Таблица токенов для аутентификации
CREATE TABLE token (
                       id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       user_id BIGINT REFERENCES users(id),
                       token VARCHAR(255) UNIQUE,
                       token_type VARCHAR(255)
                           CONSTRAINT token_token_type_check CHECK (token_type = 'BEARER'),
                       expired BOOLEAN NOT NULL,
                       revoked BOOLEAN NOT NULL
);

-- Установление владельца таблиц
ALTER TABLE projects OWNER TO postgres;
ALTER TABLE tasks OWNER TO postgres;
ALTER TABLE users OWNER TO postgres;
ALTER TABLE project_assignments OWNER TO postgres;
ALTER TABLE records OWNER TO postgres;
ALTER TABLE task_assignments OWNER TO postgres;
ALTER TABLE token OWNER TO postgres;