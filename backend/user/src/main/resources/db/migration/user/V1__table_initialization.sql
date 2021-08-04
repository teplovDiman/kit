set search_path to users;

-------------------- profile -------------------------
create table profile
(
    id          bigserial    primary key,
    type        integer      not null,
    first_name  varchar(255)     null,
    middle_name varchar(255)     null,
    last_name   varchar(255)     null,
    is_man      boolean          null,
    birth_date  date             null
);

create index if not exists profile_type_index on profile(type);
-- todo: rename all indexes like [schema_name]+[table_name]+[column(s)_name]+'index'

-------------------- permission ----------------------
create table permission
(
    id          bigserial    primary key,
    name        varchar(31)  not null unique,
    description varchar(355) not null
);

-------------------- role ----------------------------
create table role
(
    id   bigserial    primary key,
    name varchar(255) not null unique
);

-------------------- role_permission ------------------
create table permission_to_role
(
    id            bigserial primary key,
    role_id       bigint    not null references role,
    permission_id bigint    not null references permission,

    unique (role_id, permission_id)
);

create index if not exists permission_to_role_role_id_index on permission_to_role (role_id);

-------------------- users ----------------------------
create table users
(
    id            bigserial   primary key,
    username      varchar(50) not null unique,
    email         varchar(50) not null unique,
    password_hash varchar(60) not null,
    profile_id    bigint      not null references profile,
    role_id       bigint      not null references role
);

create index if not exists user_username_email_index on users(username, email);
