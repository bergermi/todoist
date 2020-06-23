CREATE SCHEMA IF NOT EXISTS todoist;
CREATE TABLE todoist.todo
(
    id                                VARCHAR(36)  NOT NULL,
    description                       VARCHAR(256)  NOT NULL,
    created_at                        TIMESTAMPTZ DEFAULT now(),
    PRIMARY KEY (id)
);

