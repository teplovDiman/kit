------------------------------------------------------
----------- M O D U L E     P R O F I L E ------------
------------------------------------------------------



-------------------- profile -------------------------
create table core_profile
(
    id                 bigserial        primary key,
    type               integer          not null,
    first_name         varchar(255)     null,
    middle_name        varchar(255)     null,
    last_name          varchar(255)     null,
    is_man             boolean          null,
    birth_date         date             null
);

create index if not exists core_profile_type_index on core_profile(type);

insert into core_profile (type, first_name, last_name, is_man, birth_date)
values (1, 'Anakin', 'Skywalker', true, now());

-------------------- permission ----------------------
create table core_permission
(
    id          bigserial    primary key,
    name        varchar(31)  not null unique,
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
    role_id       bigint                  not null references core_role
);

create index if not exists core_users_username_email_index on core_users(username, email);

insert into core_users (username, email, password_hash, profile_id, role_id)
values ('admin', 'admin@admin.com', '$2a$10$XmtWixcSIQVNuX.j3SY7ZegiojYcKp.yE1MtqgF7VAy6e1GclZITm', 1, 1);



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
('ru', 'Русский'      ), ('uk', 'Украинский'     ), ('en', 'Английский'     ), ('de', 'Немецкий'         ),
('ab', 'Абхазский'    ), ('az', 'Азербайджанский'), ('ay', 'Аймара'         ), ('sq', 'Албанский'        ),
('hy', 'Армянский'    ), ('as', 'Ассамский'      ), ('af', 'Африкаанс'      ), ('ts', 'Банту'            ),
('ba', 'Башкирский'   ), ('be', 'Белорусский'    ), ('bn', 'Бенгальский'    ), ('my', 'Бирманский'       ),
('bg', 'Болгарский'   ), ('br', 'Бретонский'     ), ('cy', 'Валлийский'     ), ('hu', 'Венгерский'       ),
('vi', 'Вьетнамский'  ), ('gd', 'Гаэльский'      ), ('nl', 'Голландский'    ), ('el', 'Греческий'        ),
('gn', 'Гуарани'      ), ('da', 'Датский'        ), ('gr', 'Древнегреческий'), ('iw', 'Древнееврейский'  ),
('zu', 'Зулу'         ), ('he', 'Иврит'          ), ('yi', 'Идиш'           ), ('in', 'Индонезийский'    ),
('ga', 'Ирландский'   ), ('is', 'Исландский'     ), ('es', 'Испанский'      ), ('it', 'Итальянский'      ),
('kn', 'Каннада'      ), ('ca', 'Каталанский'    ), ('ks', 'Кашмири'        ), ('qu', 'Кечуа'            ),
('zh', 'Китайский'    ), ('ko', 'Корейский'      ), ('kw', 'Корнский'       ), ('co', 'Корсиканский'     ),
('km', 'Кхмерский'    ), ('xh', 'Кхоса'          ), ('la', 'Латинский'      ), ('lv', 'Латышский'        ),
('mk', 'Македонский'  ), ('mg', 'Малагасийский'  ), ('ms', 'Малайский'      ), ('mt', 'Мальтийский'      ),
('mr', 'Маратхи'      ), ('mo', 'Молдавский'     ), ('mn', 'Монгольский'    ), ('na', 'Науру'            ),
('no', 'Норвежский'   ), ('pa', 'Панджаби'       ), ('fa', 'Персидский'     ), ('pl', 'Польский'         ),
('ps', 'Пушту'        ), ('rm', 'Ретороманский'  ), ('ro', 'Румынский'      ), ('rn', 'Рунди'            ),
('sa', 'Санскрит'     ), ('sr', 'Сербский'       ), ('si', 'Сингальский'    ), ('sd', 'Синдхи'           ),
('sl', 'Словенский'   ), ('so', 'Сомали'         ), ('st', 'Сото'           ), ('sw', 'Суахили'          ),
('tl', 'Тагальский'   ), ('tg', 'Таджикский'     ), ('th', 'Тайский'        ), ('ta', 'Тамильский'       ),
('te', 'Телугу'       ), ('bo', 'Тибетский'      ), ('tr', 'Турецкий'       ), ('tk', 'Туркменский'      ),
('ug', 'Уйгурский'    ), ('ur', 'Урду'           ), ('fo', 'Фарерский'      ), ('fj', 'Фиджи'            ),
('fy', 'Фризский'     ), ('ha', 'Хауса'          ), ('hi', 'Хинди'          ), ('hr', 'Хорватскосербский'),
('sv', 'Шведский'     ), ('sn', 'Шона'           ), ('eo', 'Эсперанто'      ), ('et', 'Эстонский'        ),

('fr', 'Французский'  ), ('ja', 'Японский'),
('ar', 'Арабский'     ),
('eu', 'Баскский'     ),
('bh', 'Бихарский'    ),
('wo', 'Волоф'        ),
('ka', 'Грузинский'   ),
('dr', 'Древнерусский'),
('ia', 'Интерлингва'  ),
('kk', 'Казахский'    ),
('ky', 'Киргизский'   ),
('ku', 'Курдский'     ),
('lt', 'Литовский'    ),
('mi', 'Маори'        ),
('ne', 'Непали'       ),
('pt', 'Португальский'),
('sm', 'Самоанский'   ),
('sk', 'Словацкий'    ),
('su', 'Сунданский'   ),
('tt', 'Татарский'    ),
('uz', 'Узбекский'    ),
('fi', 'Финский'      ),
('cs', 'Чешский'      ),
('jv', 'Яванский'     );

create table core_country
(
    id   bigserial    primary key,
    name varchar(255) not null unique
);
