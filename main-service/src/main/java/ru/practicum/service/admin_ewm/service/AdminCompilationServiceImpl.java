package ru.practicum.service.admin_ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.dal.CompilationRepository;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.dto.NewCompilationDto;
import ru.practicum.service.dto.UpdateCompilationRequest;
import ru.practicum.service.error.ConflictException;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.mapper.CompilationMapper;
import ru.practicum.service.model.Compilation;
import ru.practicum.service.model.Event;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        if (compilationRepository.existsByTitle(dto.getTitle())) {
            throw new ConflictException("Подборка событий с названием " +  dto.getTitle() + " уже существует!");
        }

        Compilation compilation = CompilationMapper.newCompilationToEntity(dto);
        if (dto.getEventsIds() != null && !dto.getEventsIds().isEmpty()) {
            Set<Event> events = new HashSet<>(eventRepository.findAllByIdIn(dto.getEventsIds()));
            if (events.size() != dto.getEventsIds().size()) {
                throw new NotFoundException("Некоторые события не были найдены.");
            }
            compilation.setEvents(events);
        }

        Compilation savedCompilation = compilationRepository.save(compilation);

        return CompilationMapper.toCompilationDto(savedCompilation);
    }

    @Override
    public void deleteCompilation(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new NotFoundException("Подборка событий с id=" + id + " не найдена.");
        }
        compilationRepository.deleteById(id);
    }

    @Override
    public CompilationDto updateCompilation(Long id, UpdateCompilationRequest request) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка событий с id=" + id + " не найдена."));

        if (request.getTitle() != null) {
            if (!request.getTitle().equals(compilation.getTitle()) &&
                compilationRepository.existsByTitle(request.getTitle())) {
                throw new ConflictException("Подборка событий с названием " +  request.getTitle() + " уже существует!");
            }
            compilation.setTitle(request.getTitle());
        }

        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }

        if (request.getEventsIds() != null && !request.getEventsIds().isEmpty()) {
            Set<Event> events = new HashSet<>(eventRepository.findAllByIdIn(request.getEventsIds()));
            if (events.size() != request.getEventsIds().size()) {
                throw new NotFoundException("Некоторые события не были найдены.");
            }
            compilation.setEvents(events);
        }

        Compilation savedCompilation = compilationRepository.save(compilation);

        return CompilationMapper.toCompilationDto(savedCompilation);
    }
}
