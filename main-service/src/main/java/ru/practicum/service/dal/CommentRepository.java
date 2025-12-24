package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
