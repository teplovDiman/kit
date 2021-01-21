set search_path to users;

-------------------- profile -------------------------
insert into profile (type, first_name, last_name, is_man, birth_date)
values (1, 'Che', 'Guevara', true, now());

-------------------- role ----------------------------
insert into role(name)
values ('admin'), ('moderator'), ('user');

-------------------- users ----------------------------
insert into users (username, email, password_hash, profile_id, role_id)
values ('admin', 'admin@admin.com', '$2a$10$XmtWixcSIQVNuX.j3SY7ZegiojYcKp.yE1MtqgF7VAy6e1GclZITm', 1, 1);
