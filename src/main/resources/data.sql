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

delete from TYPE_MPA;
delete from TYPE_GENRES;

insert into TYPE_GENRES values (1,'Комедия');
insert into TYPE_GENRES values (2,'Драма');
insert into TYPE_GENRES values (3,'Мультфильм');
insert into TYPE_GENRES values (4,'Триллер');
insert into TYPE_GENRES values (5,'Документальный');
insert into TYPE_GENRES values (6,'Боевик');

insert into TYPE_MPA values (1, 'G');
insert into TYPE_MPA values (2, 'PG');
insert into TYPE_MPA values (3, 'PG-13');
insert into TYPE_MPA values (4, 'R');
insert into TYPE_MPA values (5, 'NC-17');
