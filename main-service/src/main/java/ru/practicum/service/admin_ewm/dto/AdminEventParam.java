package ru.practicum.service.admin_ewm.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventParam {

    private List<Long> users;
    private List<String> states;
    private List<Long> categories;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeEnd;

    @Min(value = 0, message = "Параметр 'from' не может быть отрицательным")
    @Builder.Default
    private Integer from = 0;

    @Positive(message = "Параметр 'size' должен быть положительным числом")
    @Builder.Default
    private Integer size = 10;

    @AssertTrue(message = "Дата начала не может быть позже даты окончания")
    public boolean isValidDateRange() {
        if (rangeStart == null || rangeEnd == null) {
            return true;
        }
        return !rangeStart.isAfter(rangeEnd);
    }
}
