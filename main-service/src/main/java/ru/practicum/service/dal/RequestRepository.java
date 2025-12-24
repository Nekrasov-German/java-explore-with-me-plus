package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.model.Request;

public interface RequestRepository  extends JpaRepository<Request, Long> {
}
