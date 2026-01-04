package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.Request;

import java.util.List;

public interface RequestRepository  extends JpaRepository<Request, Long> {
    List<Request> findAllByEvent(Event eventId);
}
