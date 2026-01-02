package ru.practicum.service.admin_ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.dal.CategoryRepository;
import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.dto.NewCategoryDto;
import ru.practicum.service.error.ConflictException;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.mapper.CategoryMapper;
import ru.practicum.service.model.Category;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository repository;

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new ConflictException("Категория с названием " + dto.getName() + " уже существует.");
        }

        Category category = CategoryMapper.toCategoryEntity(dto);
        Category saved = repository.save(category);

        return CategoryMapper.toCategoryDto(saved);
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = repository.findById(catId).
                orElseThrow(() -> new NotFoundException("Категория с id=" + catId + " не найдена."));

        if (!category.getEvents().isEmpty()) {
            throw new ConflictException("Нельзя удалить категорию с id=" + catId + " так как с ней связаны события." +
                    " Количество: " + category.getEvents().size());
        }

        repository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto dto) {
        Category category = repository.findById(catId).
                orElseThrow(() -> new NotFoundException("Категория с id=" + catId + " не найдена."));

        if (!category.getName().equals(dto.getName()) &&
            repository.existsByNameAndIdNot(dto.getName(), catId)) {
            throw new ConflictException("Категория с названием " + dto.getName() + " уже существует.");
        }

        category.setName(dto.getName());
        Category updated = repository.save(category);

        return CategoryMapper.toCategoryDto(updated);
    }
}
