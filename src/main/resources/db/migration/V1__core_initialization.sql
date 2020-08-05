------------------------------------------------------
----------- M O D U L E     P R O F I L E ------------
------------------------------------------------------



-------------------- profile -------------------------
create table core_profile
(
    id                 bigserial        primary key,
    type               varchar(20)      null,
    first_name         varchar(255)     null,
    middle_name        varchar(255)     null,
    last_name          varchar(255)     null,
    birth_date         date             null
);

create index if not exists core_profile_type_index on core_profile(type);

insert into core_profile (first_name, last_name, birth_date)
values ('Anakin', 'Skywalker', now());

-------------------- permission ----------------------
create table core_permission
(
    id          bigserial    primary key,
    name        varchar(31)  not null unique,
    module_type integer      not null,
    description varchar(355) not null
);

-------------------- role ----------------------------
create table core_role
(
    id   bigserial    primary key,
    name varchar(255) not null unique
);

insert into core_role(name)
values ('admin'), ('moderator'), ('user');

-------------------- role_permission ------------------
create table core_permission_to_role
(
    id            bigserial primary key,
    role_id       bigint    not null references core_role,
    permission_id bigint    not null references core_permission,

    unique (role_id, permission_id)
);

create index if not exists core_permission_to_role_role_id_index on core_permission_to_role(role_id);

-------------------- users ----------------------------
create table core_users
(
    id            bigserial               primary key,
    username      varchar(50)             not null unique,
    email         varchar(50)             not null unique,
    password_hash varchar(60)             not null,
    profile_id    bigint                  not null references core_profile,
    role_id       bigint                  not null references core_role,
    created_at    timestamp default now() not null,
    deleted_at    timestamp                   null
);

create index if not exists core_users_username_email_index on core_users(username, email);

insert into core_users (username, email, password_hash, profile_id, role_id, created_at)
values ('admin', 'admin@admin.com', '$2a$10$XmtWixcSIQVNuX.j3SY7ZegiojYcKp.yE1MtqgF7VAy6e1GclZITm', 1, 1, now());



------------------------------------------------------
----------- M O D U L E     C O M M O N --------------
------------------------------------------------------



-------------------- language & country --------------
create table core_language
(
    id   bigserial    primary key,
    code varchar(2)   not null unique,
    name varchar(255) not null unique
);

insert into core_language (code, name)
values
('ru', 'Русский'    ), ('uk', 'Украинский'     ), ('en', 'Английский'     ), ('de', 'Немецкий'         ), ('fr', 'Французский'  ),
('ab', 'Абхазский'  ), ('az', 'Азербайджанский'), ('ay', 'Аймара'         ), ('sq', 'Албанский'        ), ('ar', 'Арабский'     ),
('hy', 'Армянский'  ), ('as', 'Ассамский'      ), ('af', 'Африкаанс'      ), ('ts', 'Банту'            ), ('eu', 'Баскский'     ),
('ba', 'Башкирский' ), ('be', 'Белорусский'    ), ('bn', 'Бенгальский'    ), ('my', 'Бирманский'       ), ('bh', 'Бихарский'    ),
('bg', 'Болгарский' ), ('br', 'Бретонский'     ), ('cy', 'Валлийский'     ), ('hu', 'Венгерский'       ), ('wo', 'Волоф'        ),
('vi', 'Вьетнамский'), ('gd', 'Гаэльский'      ), ('nl', 'Голландский'    ), ('el', 'Греческий'        ), ('ka', 'Грузинский'   ),
('gn', 'Гуарани'    ), ('da', 'Датский'        ), ('gr', 'Древнегреческий'), ('iw', 'Древнееврейский'  ), ('dr', 'Древнерусский'),
('zu', 'Зулу'       ), ('he', 'Иврит'          ), ('yi', 'Идиш'           ), ('in', 'Индонезийский'    ), ('ia', 'Интерлингва'  ),
('ga', 'Ирландский' ), ('is', 'Исландский'     ), ('es', 'Испанский'      ), ('it', 'Итальянский'      ), ('kk', 'Казахский'    ),
('kn', 'Каннада'    ), ('ca', 'Каталанский'    ), ('ks', 'Кашмири'        ), ('qu', 'Кечуа'            ), ('ky', 'Киргизский'   ),
('zh', 'Китайский'  ), ('ko', 'Корейский'      ), ('kw', 'Корнский'       ), ('co', 'Корсиканский'     ), ('ku', 'Курдский'     ),
('km', 'Кхмерский'  ), ('xh', 'Кхоса'          ), ('la', 'Латинский'      ), ('lv', 'Латышский'        ), ('lt', 'Литовский'    ),
('mk', 'Македонский'), ('mg', 'Малагасийский'  ), ('ms', 'Малайский'      ), ('mt', 'Мальтийский'      ), ('mi', 'Маори'        ),
('mr', 'Маратхи'    ), ('mo', 'Молдавский'     ), ('mn', 'Монгольский'    ), ('na', 'Науру'            ), ('ne', 'Непали'       ),
('no', 'Норвежский' ), ('pa', 'Панджаби'       ), ('fa', 'Персидский'     ), ('pl', 'Польский'         ), ('pt', 'Португальский'),
('ps', 'Пушту'      ), ('rm', 'Ретороманский'  ), ('ro', 'Румынский'      ), ('rn', 'Рунди'            ), ('sm', 'Самоанский'   ),
('sa', 'Санскрит'   ), ('sr', 'Сербский'       ), ('si', 'Сингальский'    ), ('sd', 'Синдхи'           ), ('sk', 'Словацкий'    ),
('sl', 'Словенский' ), ('so', 'Сомали'         ), ('st', 'Сото'           ), ('sw', 'Суахили'          ), ('su', 'Сунданский'   ),
('tl', 'Тагальский' ), ('tg', 'Таджикский'     ), ('th', 'Тайский'        ), ('ta', 'Тамильский'       ), ('tt', 'Татарский'    ),
('te', 'Телугу'     ), ('bo', 'Тибетский'      ), ('tr', 'Турецкий'       ), ('tk', 'Туркменский'      ), ('uz', 'Узбекский'    ),
('ug', 'Уйгурский'  ), ('ur', 'Урду'           ), ('fo', 'Фарерский'      ), ('fj', 'Фиджи'            ), ('fi', 'Финский'      ),
('fy', 'Фризский'   ), ('ha', 'Хауса'          ), ('hi', 'Хинди'          ), ('hr', 'Хорватскосербский'), ('cs', 'Чешский'      ),
('sv', 'Шведский'   ), ('sn', 'Шона'           ), ('eo', 'Эсперанто'      ), ('et', 'Эстонский'        ), ('jv', 'Яванский'     ),
('ja', 'Японский');

create table core_country
(
    id   bigserial    primary key,
    name varchar(255) not null unique
);



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


