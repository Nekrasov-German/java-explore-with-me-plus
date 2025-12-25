package ru.practicum.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.service.model.enums.Status;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    // Заявка на участие в событии
    LocalDateTime created; // в swagger формат 2022-09-06T21:10:05.432
    Long event;
    Long id;
    Long requester;
    Status status;
}
