package ru.practicum.service.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.dto.NewCategoryDto;
import ru.practicum.service.model.Category;

@UtilityClass
public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        /*return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();*/                                    // нужен builder в CategoryDto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category toEntity(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }
}
