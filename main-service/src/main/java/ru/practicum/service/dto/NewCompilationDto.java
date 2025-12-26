package ru.practicum.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    // Подборка событий
    @JsonProperty(value = "events")
    List<Long> eventsIds;

    Boolean pinned;

    String title;
}
