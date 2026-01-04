package ru.practicum.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;

    @NotNull
    @JsonProperty(value = "category")
    CategoryDto categoryDto;

    Long confirmedRequests;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime createdOn;

    @Size(min = 20, max = 7000)
    String description;

    @NotNull
    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    Long id;

    @NotNull
    @JsonProperty(value = "initiator")
    UserShortDto initiatorDto;

    @NotNull
    @JsonProperty(value = "location")
    LocationDto locationDto;

    @NotNull
    Boolean paid;

    Integer participantLimit;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime publishedOn;

    Boolean requestModeration;

    State state;

    @NotBlank
    @Size(min = 3, max = 120)
    String title;

    Long views;
}
