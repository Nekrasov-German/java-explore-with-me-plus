package ru.practicum.service.public_ewm.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.service.dal.CompilationRepository;
import ru.practicum.service.dto.CompilationDto;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    final CompilationRepository compilationRepository;
    final StatClient statClient;

    @Override
    public CompilationDto findCompilations(Boolean pinned, Integer from, Integer size) {

        return null;
    }
}
