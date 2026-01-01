package ru.practicum.service.private_ewm.service;

import org.springframework.stereotype.Service;
import ru.practicum.service.dto.*;

import java.util.List;

@Service
public class PrivateServiceImpl implements PrivateService {
    @Override
    public List<EventShortDto> getEventsByOwner(Long userId, Long from, Long size) {
        return List.of();
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        return null;
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
