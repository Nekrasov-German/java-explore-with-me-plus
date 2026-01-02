package ru.practicum.service.private_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.service.dal.CategoryRepository;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dal.UserRepository;
import ru.practicum.service.dto.*;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.error.ValidationException;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.model.Category;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateServiceImpl implements PrivateService {
    private final StatClient client;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public List<EventShortDto> getEventsByOwner(Long userId, Long from, Long size) {
        return List.of();
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto, HttpServletRequest request) {
        Category category = categoryRepository.findById(newEventDto.getCategoryId())
                .orElseThrow(() -> new ValidationException("Категория не указана"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует."));
        Event event = eventRepository.save(EventMapper.newEventDtoToEvent(newEventDto, user, category));
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);
        // Возможно нужно заполнить поле views это не точно так как событие только создано!!!
        return eventFullDto;
    }

    @Override
    public EventFullDto getInfoEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getInfoRequest(Long userId, Long eventId) {
        return List.of();
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequest(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getInfoOnParticipation(Long userId) {
        return List.of();
    }

    @Override
    public ParticipationRequestDto createRequestForParticipation(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto canceledRequestForParticipation(Long userId, Long requestId) {
        return null;
    }
}
