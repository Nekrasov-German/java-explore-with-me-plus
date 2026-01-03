package ru.practicum.service.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.enums.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = """
                SELECT *
                FROM events e
                WHERE e.state = 'PUBLISHED'
                AND (:text IS NULL
                    OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%'))
                    OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))
                    )
                AND (:paid IS NULL OR e.paid = :paid)
                AND (:categories IS NULL OR e.category_id IN (:categories))
                AND (:rangeStart IS NULL OR e.event_date >= :rangeStart)
                AND (:rangeEnd IS NULL OR e.event_date <= :rangeEnd)
                AND (:onlyAvailable = FALSE
                    OR e.participant_limit = 0
                    OR e.confirmed_requests < e.participant_limit
                    )
                ORDER BY e.event_date ASC
                LIMIT :size OFFSET :from
                """,
            nativeQuery = true)
    List<Event> findPublicEventsNative(@Param("text") String text,
                                       @Param("categories") List<Long> categories,
                                       @Param("paid") Boolean paid,
                                       @Param("rangeStart") LocalDateTime rangeStart,
                                       @Param("rangeEnd") LocalDateTime rangeEnd,
                                       @Param("onlyAvailable") Boolean onlyAvailable,
                                       @Param("from") Integer from,
                                       @Param("size") Integer size);

    List<Event> findAllByIdIn(Set<Long> ids);

    Page<Event> findByInitiator_Id(Long userId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) " +
            "ORDER BY e.createdOn DESC")
    List<Event> findEventByAdmin(
            @Param("users") List<Long> users,
            @Param("states") List<State> states,
            @Param("categories") List<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);
}
