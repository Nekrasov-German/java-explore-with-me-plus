package ru.practicum.service.public_ewm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.public_ewm.service.PublicCategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicCategoryController {
    final PublicCategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategory(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Long from,
                                         @RequestParam(value = "size", defaultValue = "10") @PositiveOrZero Long size,
                                         HttpServletRequest request) {
        log.info("PublicCategoryController: взов эндпоинта GET /category с параметрами -- from:{}, size:{}", from, size);

        return categoryService.findCategories(from, size, request);
    }
}
