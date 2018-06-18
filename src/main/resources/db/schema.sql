CREATE TABLE USERS (
  id       INT          NOT NULL AUTO_INCREMENT,
  email    varchar(255) NOT NULL UNIQUE,
  name     varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role     varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE APPS (
  id      INT          NOT NULL AUTO_INCREMENT,
  name    varchar(255) NOT NULL,
  type    VARCHAR(7)   NOT NULL,
  user_id INT          NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE APP_CONTENT_TYPES (
  app_id        INT NOT NULL,
  content_types VARCHAR(5) NOT NULL
);

