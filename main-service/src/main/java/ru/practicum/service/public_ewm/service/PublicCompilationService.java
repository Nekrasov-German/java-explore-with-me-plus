package ru.practicum.service.public_ewm.service;

import ru.practicum.service.dto.CompilationDto;

public interface PublicCompilationService {
    CompilationDto findCompilations(Boolean pinned, Integer from, Integer size);
}
