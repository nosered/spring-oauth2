drop table if exists users;
create table if not exists users (
  id IDENTITY PRIMARY KEY,
  username VARCHAR(255),
  password VARCHAR(255),
  enabled BOOLEAN,
  uuid UUID UNIQUE
);

drop table if exists authorities;
create table if not exists authorities (
  id IDENTITY PRIMARY KEY,
  authority VARCHAR(255)
);

drop table if exists user_authorities;
create table if not exists user_authorities(
  id IDENTITY PRIMARY KEY,
  user_id BIGINT,
  authority_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (authority_id) REFERENCES authorities(id)
);