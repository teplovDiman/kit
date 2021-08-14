set search_path to note;

-------------------- note ----------------------------
create table note
(
    id         bigserial    primary key,
    title      varchar(255)               not null,
    value      text                       not null,
    created_by bigint                     not null references users.users,
    created_at timestamp    default now() not null
);

-------------------- note_tag ------------------------

create table note_tag
(
    id         bigserial    primary key,
    name       varchar(31)                not null unique,
    created_by bigint                         null references users.users,
    created_at timestamp    default now() not null
);

-------------------- note_to_note_tag ----------------

create table note_to_note_tag
(
    note_id     bigint not null references note,
    note_tag_id bigint not null references note_tag,

    unique (note_id, note_tag_id)
);
