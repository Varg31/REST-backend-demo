insert into usr (user_id, username, password, enabled, email)
values (1, 'admin', '1111', true, 'ashapravsky@gmail.com');

insert into user_role (user_id, authorities)
values (1, 'USER'), (1, 'ADMIN');