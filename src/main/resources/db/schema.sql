CREATE TABLE USERS (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  email    varchar(255) NOT NULL UNIQUE,
  name     varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role     varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE APPS (
  id      BIGINT       NOT NULL AUTO_INCREMENT,
  name    varchar(255) NOT NULL,
  type    VARCHAR(7)   NOT NULL,
  user_id BIGINT       NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES USERS(id)
);

CREATE TABLE APP_CONTENT_TYPES (
  app_id        BIGINT     NOT NULL,
  content_types VARCHAR(5) NOT NULL,
  FOREIGN KEY (app_id) REFERENCES APPS(id)
);

