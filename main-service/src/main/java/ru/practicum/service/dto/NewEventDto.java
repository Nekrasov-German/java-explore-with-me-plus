package ru.practicum.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    // Новое событие
    String annotation;

    @JsonProperty(value = "category")
    Long categoryId;

    //Long id; В сваггере нет этого поля, возможно, стоит удалить

    String description;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    @JsonProperty(value = "location")
    LocationDto locationDto;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    String title;
}
