package ru.otus.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.domain.Comment;

@Data
@AllArgsConstructor
public class CommentDto {
    private final String id;
    private final String bookId;
    private final String nickname;
    private final String message;

    public static Comment toDomainObject(CommentDto dto) {
        return Comment.builder().id(dto.id)
                .bookId(dto.bookId)
                .nickname(dto.nickname)
                .message(dto.message).build();
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBookId(),
                comment.getNickname(), comment.getMessage());
    }
}
