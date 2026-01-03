package ru.practicum.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.service.model.enums.State;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    String annotation;

    @JsonProperty(value = "category")
    CategoryDto categoryDto;

    Long confirmedRequests;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime createdOn;

    String description;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    Long id;

    @JsonProperty(value = "initiator")
    UserShortDto initiatorDto;

    @JsonProperty(value = "location")
    LocationDto locationDto;

    Boolean paid;

    Integer participantLimit;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime publishedOn;

    Boolean requestModeration;

    State state;

    String title;

    Long views;
}
