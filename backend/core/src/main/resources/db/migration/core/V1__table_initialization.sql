set search_path to core;

-------------------- language & country --------------
create table language
(
    id   bigserial    primary key,
    code varchar(2)   not null unique,
    name varchar(255) not null unique
);

create table country
(
    id   bigserial    primary key,
    name varchar(255) not null unique
);
