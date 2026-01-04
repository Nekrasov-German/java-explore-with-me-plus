package ru.practicum.service.admin_ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.response.HitsCounterResponseDto;
import ru.practicum.service.admin_ewm.dto.AdminEventParam;
import ru.practicum.service.dal.CategoryRepository;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dto.EventFullDto;
import ru.practicum.service.dto.UpdateEventAdminRequest;
import ru.practicum.service.dto.enums.AdminStateAction;
import ru.practicum.service.error.ConflictException;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.error.ValidationException;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.model.Category;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.Location;
import ru.practicum.service.model.enums.State;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final StatClient  statClient;

    private final String URI_EVENT_ENDPOINT = "/events/";

    @Override
    public List<EventFullDto> getFullEvents(AdminEventParam params) {
        List<State> states = convertStatesEnum(params.getStates());

        Pageable pageable = PageRequest.of(params.getFrom(), params.getSize());

        List<Event> events = eventRepository.findEventByAdmin(
                params.getUsers(),
                states,
                params.getCategories(),
                params.getRangeStart(),
                params.getRangeEnd(),
                pageable
        );

        return getEventFullDtoWithStatistics(events);
    }

    private List<State> convertStatesEnum(List<String> states) {
        if (states == null || states.isEmpty()) {
            return null;
        }
        return states.stream()
                .map(state -> {
                    try {
                        return State.valueOf(state.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new ValidationException("Некорректное состояние события " + state);
                    }
                })
                .toList();
    }

    private List<EventFullDto> getEventFullDtoWithStatistics(List<Event> events) {
        if (events.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> uris = events.stream()
                .map(event -> URI_EVENT_ENDPOINT + event.getId())
                .toList();

        List<HitsCounterResponseDto> stats = statClient.getHits(uris, false);

        Map<String, Long> eventIdEventHits = stats.stream()
                .collect(Collectors.toMap(
                        HitsCounterResponseDto::getUri,
                        HitsCounterResponseDto::getHits
                ));

        return events.stream()
                .map(event -> {
                    EventFullDto dto = EventMapper.eventToEventFullDto(event);
                    Long views = eventIdEventHits.getOrDefault(URI_EVENT_ENDPOINT + event.getId(), 0L);
                    dto.setViews(views);
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено."));

        if (request.getStateAction() != null) {

            if (request.getStateAction() == AdminStateAction.PUBLISH_EVENT) {
                if (event.getState() != State.PENDING) {
                    throw new ConflictException("Cобытие можно публиковать, только если оно в состоянии ожидания публикации");
                }
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());

            } else if (request.getStateAction() == AdminStateAction.REJECT_EVENT) {
                if (event.getState() == State.PUBLISHED) {
                    throw new ConflictException("Cобытие можно отклонить, только если оно еще не опубликовано");
                }
                event.setState(State.CANCELED);
            }
        }

        updateEventFields(event, request);
        Event updatedEvent = eventRepository.save(event);
        EventFullDto dto = EventMapper.eventToEventFullDto(updatedEvent);
        Long views = getEventViews(updatedEvent.getId());
        dto.setViews(views);

        return dto;
    }

    private void updateEventFields(Event event, UpdateEventAdminRequest request) {
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Категория с id=" + request.getCategoryId() + " не найдена."));
            event.setCategory(category);
        }

        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }

        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }

        if (request.getLocationDto() != null) {
            event.setLocation(
                    Location.builder()
                            .lat(request.getLocationDto().getLat())
                            .lon(request.getLocationDto().getLon())
                            .build());
        }

        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }

        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }

        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
        }

        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
    }

    private Long getEventViews(Long eventId) {
        String uri = URI_EVENT_ENDPOINT + eventId;

        List<HitsCounterResponseDto> stats = statClient.getHits(
                List.of(uri),
                false
        );

        return stats.stream()
                .filter(dto -> dto.getUri().equals(uri))
                .map(HitsCounterResponseDto::getHits)
                .findFirst()
                .orElse(0L);
    }
}
