INSERT INTO clientes (name, lastname, email, create_at) VALUES('Joshua', 'Carrasco', 'joshuacarrasco02@gmail.com', '2018-01-01');
INSERT INTO clientes (name, lastname, email, create_at) VALUES('Joshua2', 'Carrasco', 'joshuacarrasco03@gmail.com', '2019-01-01');
INSERT INTO clientes (name, lastname, email, create_at) VALUES('Joshua3', 'Carrasco', 'joshuacarrasco05@gmail.com', '2020-01-01');
INSERT INTO clientes (name, lastname, email, create_at) VALUES('BD', 'pgsql', 'nuevo@gmail.com', '2020-01-01');

insert into users (username,password,enabled) values ('user','$2a$10$rhdZQUU.3Ys.f.oTKVdFRe4WWLosdGmpQZRQjqS1186/uYvV2r/zK',true);
insert into users (username,password,enabled) values ('admin','$2a$10$3YXVxJSqYAkIqA9gZv9bheSphHDMof.VxXfLDMeZgj8v5jc0SP6lS',true);

insert into authorities (user_id,authority) values (1,'ROLE_USER');
insert into authorities (user_id,authority) values (2,'ROLE_USER');
insert into authorities (user_id,authority) values (2,'ROLE_ADMIN');
