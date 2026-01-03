package ru.practicum.service.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.service.dto.ParticipationRequestDto;
import ru.practicum.service.model.Request;

@UtilityClass
public class RequestMapper {
    public ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .requesterId(request.getRequester().getId())
                .eventId(request.getEvent().getId())
                .created(request.getCreated())
                .status(request.getStatus())
                .id(request.getId())
                .build();
    }
}
