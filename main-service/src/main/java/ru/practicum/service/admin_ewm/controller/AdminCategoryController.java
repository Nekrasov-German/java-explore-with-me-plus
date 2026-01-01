package ru.practicum.service.admin_ewm.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin_ewm.service.AdminCategoryService;
import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.dto.NewCategoryDto;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto newCategory(
            @RequestBody @Valid NewCategoryDto dto
    ) {
        return service.createCategory(dto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable(name = "catId") @Positive Long catId
    ) {
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(
            @PathVariable(name = "catId") @Positive Long catId,
            @RequestBody @Valid CategoryDto dto
    ) {
        return service.updateCategory(catId, dto);
    }
}
