package ru.practicum.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    // Подборка событий
    List<Long> events;
    Boolean pinned;
    String title;
}
