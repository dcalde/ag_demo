ALTER ROLE ag_demo SET SEARCH_PATH = ag, public;

ALTER SCHEMA ag OWNER TO ag_demo;

CREATE SEQUENCE s_todo;
ALTER SEQUENCE s_todo OWNER TO ag_demo;

CREATE TABLE todo
(
  id        BIGINT                      NOT NULL DEFAULT nextval('s_todo'),
  created   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  completed BOOLEAN                     NOT NULL,
  text      CHARACTER VARYING(50)       NOT NULL,
  CONSTRAINT todo_pkey PRIMARY KEY (id)
);
ALTER TABLE todo
  OWNER TO ag_demo;
