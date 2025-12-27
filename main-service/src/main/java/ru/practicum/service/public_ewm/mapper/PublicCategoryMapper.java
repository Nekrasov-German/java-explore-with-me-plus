package ru.practicum.service.public_ewm.mapper;

import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.model.Category;

public class PublicCategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto cd = new CategoryDto();
        cd.setId(category.getId());
        cd.setName(category.getName());

        return cd;
    }
}
