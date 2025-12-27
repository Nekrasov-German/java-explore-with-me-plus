package ru.practicum.service.admin_ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin_ewm.service.AdminCategoryService;
import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.dto.NewCategoryDto;

@RestController
@RequestMapping(name = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    public CategoryDto newCategory(
            @RequestBody NewCategoryDto dto
    ) {
        return service.createCategory(dto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(
            @PathVariable(name = "catId") Long catId
    ) {
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(
            @PathVariable(name = "catId") Long catId,
            @RequestBody NewCategoryDto dto
    ) {
        return service.updateCategory(catId, dto);
    }
}
