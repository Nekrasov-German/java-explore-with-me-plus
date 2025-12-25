package ru.practicum.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.service.model.enums.State;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequest {
    // Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
    String annotation;
    Long category;
    String description;
    LocalDateTime eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    State state;
    String title;
}
