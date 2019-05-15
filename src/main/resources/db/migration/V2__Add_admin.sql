insert into usr (user_id, username, password, enabled)
values (1, 'admin', '1111', true);

insert into user_role (user_id, authorities)
values (1, 'USER'), (1, 'ADMIN');