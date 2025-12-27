package ru.practicum.service.public_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.service.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> findCategories(Long from, Long size, HttpServletRequest request);

    CategoryDto findById(Long catId, HttpServletRequest request);
}
