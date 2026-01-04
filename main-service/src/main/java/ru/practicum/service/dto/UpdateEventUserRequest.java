package ru.practicum.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.service.dto.enums.UserStateAction;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    // Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
    String annotation;

    @JsonProperty(value = "category")
    Long categoryId;

    String description;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    @JsonProperty(value = "location")
    LocationDto locationDto;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    UserStateAction stateAction;

    String title;
}
