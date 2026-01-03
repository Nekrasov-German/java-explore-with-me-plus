package ru.practicum.service.admin_ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.service.admin_ewm.dto.AdminEventParam;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dto.EventFullDto;
import ru.practicum.service.dto.UpdateEventAdminRequest;
import ru.practicum.service.error.ValidationException;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.enums.State;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final StatClient  statClient;

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

        return null;
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

    private EventFullDto getEventFullDtoWithStatistics(Event event) {
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);

        return null;
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long id, UpdateEventAdminRequest request) {
        return null;
    }
}
