package ru.practicum.service.error;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {
    // Сведения об ошибке
    List<String> errors;
    String message;
    String reason;
    String status;
    String timestamp;
}
