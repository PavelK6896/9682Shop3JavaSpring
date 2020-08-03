drop table if exists users;
create table users (
  id                    bigserial,
  phone                 VARCHAR(30) not null UNIQUE,
  password              VARCHAR(80) not null,
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
  id                    serial,
  name                  VARCHAR(50) not null,
  primary key (id)
);

drop table if exists users_roles;
create table users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  primary key (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

insert into roles (name)
values
('USER'), ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (phone, password, first_name, last_name, email)
values
('1','$2y$12$2LVAI8g8ci9Iq/Zod6Zb4uQYw1cLxTIG669BlOJP7W8Xo6dgeHVXa','admin','admin','admin@gmail.com'),
('2','$2y$12$2LVAI8g8ci9Iq/Zod6Zb4uQYw1cLxTIG669BlOJP7W8Xo6dgeHVXa','user','user','user@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2);
