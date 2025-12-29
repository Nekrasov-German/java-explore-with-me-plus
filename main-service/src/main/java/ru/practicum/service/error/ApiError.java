package ru.practicum.service.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiError {
    // Сведения об ошибке
    List<String> errors;
    String message;
    String reason;
    String status;
    String timestamp;
}
