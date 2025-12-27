package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.service.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = """
            SELECT *
            FROM categories
            ORDER BY id
            LIMIT :size OFFSET :from
            """,
            nativeQuery = true)
    List<Category> findCategories(@Param("from") Long from, @Param("size") Long size);
}
