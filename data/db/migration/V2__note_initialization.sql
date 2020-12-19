------------------------------------------------------
----------- M O D U L E     N O T E ------------------
------------------------------------------------------


-------------------- note ----------------------------
create table note
(
  id           bigserial    primary key,
  user_id      bigint       not null references core_users,
  created_at   timestamp    default now() not null,
  title        varchar(500)     null,
  value        text         not null
);

create index if not exists note_user_id_index on note(user_id);

-------------------- note_tag ------------------------

create table note_tag
(
  id   bigserial    primary key,
  name varchar(500) not null unique
);

create index if not exists note_tag_name_index on note_tag(name);

-------------------- note_tag_to_user ----------------

create table note_tag_to_user
(
  id          bigserial primary key,
  user_id     bigint    not null references core_users,
  note_tag_id bigint    not null references note_tag,

  unique (note_tag_id, user_id)
);
-------------------- note_tag_to_note ----------------

create table note_tag_to_note
(
  note_id     bigint not null references note,
  note_tag_id bigint not null references note_tag,

  unique (note_id, note_tag_id)
);



------------------------------------------------------
----------- M O D U L E     C O N T A C T S ----------
------------------------------------------------------


-------------------- book_to_participant -------------
create table contact
(
  id          bigserial     primary key,
  user_id     bigint        not null references core_users,   -- contact owner
  profile_id  bigint            null references core_profile,
  description varchar(1023)     null,
  aliases     jsonb             null,
  user_to     bigint            null references core_users,   -- if user registered in system

  unique (user_id, profile_id)
);

create index if not exists contact_user_id_index on contact(user_id);