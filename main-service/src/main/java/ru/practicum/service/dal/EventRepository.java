package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
