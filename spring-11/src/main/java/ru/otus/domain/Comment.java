package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "message", nullable = false)
    private String message;
}