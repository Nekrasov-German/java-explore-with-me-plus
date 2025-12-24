package ru.practicum.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {
    // Подборка событий
    List<EventShortDto> events;
    Integer id;
    Boolean pinned;
    String title;
}
