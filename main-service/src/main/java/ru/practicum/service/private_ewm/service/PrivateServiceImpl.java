package ru.practicum.service.private_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.response.HitsCounterResponseDto;
import ru.practicum.service.dal.CategoryRepository;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dal.RequestRepository;
import ru.practicum.service.dal.UserRepository;
import ru.practicum.service.dto.*;
import ru.practicum.service.error.ConflictException;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.error.ValidationException;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.mapper.RequestMapper;
import ru.practicum.service.model.Category;
import ru.practicum.service.model.Event;
import ru.practicum.service.model.Request;
import ru.practicum.service.model.User;
import ru.practicum.service.model.enums.State;
import ru.practicum.service.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateServiceImpl implements PrivateService {
    private static final Logger log = LoggerFactory.getLogger(PrivateServiceImpl.class);
    private final LocalDateTime VERY_PAST = LocalDateTime.of(2000, 1, 1, 0, 0);

    private final StatClient client;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;


    @Override
    public List<EventShortDto> getEventsByOwner(Long userId, Long from, Long size) { //TODO добавить просмотры
        int page = from.intValue() / size.intValue();
        Pageable pageable = PageRequest.of(page, size.intValue());

        Page<Event> eventPage = eventRepository.findByInitiator_Id(userId, pageable);
        return eventPage.getContent().stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto, HttpServletRequest request) { //TODO проверить нужны ли просмотры т.к. только создано
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Время события должно быть за два часа до события.");
        }

        Category category = categoryRepository.findById(newEventDto.getCategoryId())
                .orElseThrow(() -> new ValidationException("Категория не указана"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует."));


        Event event = eventRepository.save(EventMapper.newEventDtoToEvent(newEventDto, user, category));
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);

//        List<HitsCounterResponseDto> hitsCounter = client.getHits(VERY_PAST,
//                LocalDateTime.now(),
//                List.of(request.getRequestURI()),
//                true);
//        Long views = hitsCounter.isEmpty() ? 0L : hitsCounter.getFirst().getHits();
//
//        eventFullDto.setViews(views);
        return eventFullDto;
    }

    @Override
    public EventFullDto getInfoEvent(Long userId, Long eventId, HttpServletRequest request) { //TODO добавить просмотры
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не найдено."));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует."));
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);

        List<HitsCounterResponseDto> hitsCounter = client.getHits(VERY_PAST,
                LocalDateTime.now(),
                List.of(request.getRequestURI()),
                true);
        Long views = hitsCounter.isEmpty() ? 0L : hitsCounter.getFirst().getHits();

        eventFullDto.setViews(views);
        return eventFullDto;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) { //TODO проверить нужны ли просмотры т.к. только обновлено
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не найдено."));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует."));
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Данное событие нельзя изменить.");
        }
        Optional<Category> category = categoryRepository.findById(updateEventUserRequest.getCategoryId());

        Event updateEvent = eventRepository
                .save(EventMapper.UpdateEventDtoToEvent(event, updateEventUserRequest, category));

        return EventMapper.eventToEventFullDto(updateEvent);
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
    public ParticipationRequestDto createRequestForParticipation(Long userId, Long eventId) { //TODO проверить после появления admin/events/{eventId}
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (event.getInitiator().equals(user)) {
            throw new ConflictException("инициатор события не может добавить запрос на участие в своём событии.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("нельзя участвовать в неопубликованном событии.");
        }
        if (event.getParticipantLimit() == event.getRequests().size()) {
            throw new ConflictException("у события достигнут лимит запросов на участие");
        }

        Request request = Request.builder()
                .requester(user)
                .event(event)
                .build();

        if (!event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        }
        //        Обратите внимание:
        //TODO проверка на уникальность повторного запроса возможно вернется не 409
        //нельзя добавить повторный запрос (Ожидается код ошибки 409)
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto canceledRequestForParticipation(Long userId, Long requestId) {
        return null;
    }
}
