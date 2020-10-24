insert into AUTHORS (ID, FIO)
values (1, 'Frank Baum'),
       (2, 'Joanne Rowling');

insert into GENRES (ID, NAME)
values (1, 'fairy tail'),
       (2, 'fantasy');

insert into BOOKS (ID, TITLE, GENRE_ID)
values (1, 'The Wizard of Oz', 1),
       (2, 'Harry Potter', 2);

insert into COMMENTS (ID, BOOK_ID, NICKNAME, MESSAGE)
values (1, 1, 'Vitaliy', 'It''s awesome book'),
       (2, 2, 'Max', 'I like Harry Potter'),
       (3, 2, 'Mitya', 'Its fairytale, so i don''t like it');


insert into BOOKS_AUTHORS(BOOK_ID, AUTHOR_ID)
values (1, 1),
       (2, 2);