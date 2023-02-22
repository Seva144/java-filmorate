create table if not exists TYPE_GENRES
(
    ID   INTEGER not null,
    NAME CHARACTER VARYING(50),
    constraint TYPE_GENRES_PK
        primary key (ID)
);

create table if not exists TYPE_MPA
(
    ID   INTEGER not null,
    NAME CHARACTER VARYING(50),
    constraint TYPE_MPA_PK
        primary key (ID)
);

create table if not exists FILMS
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING(50),
    DESCRIPTION  CHARACTER VARYING(1000),
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    MPA_ID       INTEGER default 0,
    constraint FILMS_PK
        primary key (FILM_ID),
    constraint MPA___FK
        foreign key (MPA_ID) references TYPE_MPA
);

create table if not exists GENRES
(
    GENRE_ID INTEGER auto_increment,
    FILM_ID  INTEGER,
    TYPE_ID  INTEGER,
    constraint GENRES_FILMS_PK
        primary key (GENRE_ID),
    constraint GENRES_CONSTRAINT
        unique (FILM_ID, TYPE_ID),
    constraint FILMS__FK
        foreign key (FILM_ID) references FILMS,
    constraint TYPE_ID_GENRES_FK
        foreign key (TYPE_ID) references TYPE_GENRES
);

create table if not exists USERS
(
    USER_ID   INTEGER auto_increment,
    USER_NAME CHARACTER VARYING(50),
    EMAIL     CHARACTER VARYING(50),
    BIRTHDAY  DATE,
    LOGIN     CHARACTER VARYING(50) not null,
    constraint USERS_PK
        primary key (USER_ID)
);

create table if not exists FRIENDSHIP
(
    FRIENDSHIP_ID INTEGER auto_increment,
    USER_ID       INTEGER               not null,
    FRIEND_ID     INTEGER auto_increment,
    STATUS        BOOLEAN default FALSE not null,
    constraint FRIENDSHIP_PK
        primary key (FRIENDSHIP_ID),
    constraint USER2_FRIEND__FK
        foreign key (FRIEND_ID) references USERS,
    constraint USER_FRIEND__FK
        foreign key (USER_ID) references USERS
);

create table if not exists LIKES
(
    LIKES_ID INTEGER auto_increment,
    FILM_ID  INTEGER default 0,
    USER_ID  INTEGER default 0,
    constraint LIKES_PK
        primary key (LIKES_ID),
    constraint FILM___FK
        foreign key (FILM_ID) references FILMS,
    constraint USER___FK
        foreign key (USER_ID) references USERS
);

delete from TYPE_GENRES;
delete from TYPE_MPA;


delete from GENRES;
alter table GENRES alter column
    GENRE_ID restart with 1;
delete from FRIENDSHIP;
alter table FRIENDSHIP alter column
    FRIENDSHIP_ID restart with 1;
delete from LIKES;
alter table LIKES alter column
    LIKES_ID restart with 1;
delete from USERS;
alter table USERS alter column
    USER_ID restart with 1;
delete from FILMS;
alter table FILMS alter column
    FILM_ID restart with 1;


