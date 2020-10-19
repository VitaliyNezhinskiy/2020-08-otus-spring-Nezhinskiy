INSERT INTO AUTHORS (ID, FIO)
VALUES (1, 'Frank Baum'),
       (2, 'Joanne Rowling'),
       (3, 'John Ronald Reuel Tolkien');
INSERT INTO GENRES (ID, NAME)
VALUES (1, 'fairy tail'),
       (2, 'fantasy');
INSERT INTO BOOKS (ID, TITLE, GENRE_ID)
VALUES (1, 'The Wizard of Oz', 1),
       (2, 'Harry Potter', 1),
       (3, 'The Lord of the Rings', 1);
INSERT INTO COMMENTS (ID, BOOK_ID, NICKNAME, MESSAGE)
VALUES (1, 1,'Vitaliy', 'It''s awesome book'),
       (2, 2,'Max', 'I like Harry Potter'),
       (3, 3,'Mitya', 'Its fairytale, so i don''t like it');
INSERT INTO BOOKS_AUTHORS(BOOK_ID, AUTHOR_ID)
VALUES (1, 1),
       (2, 2);