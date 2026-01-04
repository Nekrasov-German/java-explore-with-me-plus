package ru.practicum.service.admin_ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.admin_ewm.statistics.StatisticsService;
import ru.practicum.service.dal.CompilationRepository;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.dto.NewCompilationDto;
import ru.practicum.service.dto.UpdateCompilationRequest;
import ru.practicum.service.error.ConflictException;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.mapper.CompilationMapper;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.model.Compilation;
import ru.practicum.service.model.Event;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final StatisticsService  statisticsService;

    private final String URI_EVENT_ENDPOINT = "/events/";

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        if (compilationRepository.existsByTitle(dto.getTitle())) {
            throw new ConflictException("Подборка событий с названием " +  dto.getTitle() + " уже существует!");
        }

        Compilation compilation = CompilationMapper.newCompilationToEntity(dto);

        Set<EventShortDto> eventShortDtos = Collections.emptySet();

        if (dto.getEventsIds() != null && !dto.getEventsIds().isEmpty()) {
            Set<Event> events = new HashSet<>(eventRepository.findAllByIdIn(dto.getEventsIds()));
            if (events.size() != dto.getEventsIds().size()) {
                throw new NotFoundException("Некоторые события не были найдены.");
            }
            eventShortDtos = getEventShortDto(events);
            compilation.setEvents(events);
        }

        Compilation savedCompilation = compilationRepository.save(compilation);

        return CompilationMapper.toCompilationDto(savedCompilation, eventShortDtos);
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
                throw new ConflictException("Подборка событий с названием " + request.getTitle() + " уже существует!");
            }
            compilation.setTitle(request.getTitle());
        }

        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }

        Set<Event> events = compilation.getEvents();

        if (request.getEventsIds() != null) {
            if (request.getEventsIds().isEmpty()) {
                events = Collections.emptySet();
            } else {
                events = new HashSet<>(eventRepository.findAllByIdIn(request.getEventsIds()));
                if (events.size() != request.getEventsIds().size()) {
                    throw new NotFoundException("Некоторые события не были найдены.");
                }
            }
            compilation.setEvents(events);
        }

        Compilation savedCompilation = compilationRepository.save(compilation);

        Set<EventShortDto> eventShortDtos = events != null && !events.isEmpty()
                ? getEventShortDto(events)
                : Collections.emptySet();

        return CompilationMapper.toCompilationDto(savedCompilation, eventShortDtos);
    }

    private Set<EventShortDto> getEventShortDto(Set<Event> events) {
        List<String> uris = events.stream()
                .map(event -> URI_EVENT_ENDPOINT + event.getId())
                .toList();

        Map<String, Long> eventIdEventHits = statisticsService.getViewsByUris(uris, false);

        return events.stream()
                .map(event -> {
                    Long views = eventIdEventHits.getOrDefault(URI_EVENT_ENDPOINT + event.getId(), 0L);
                    return EventMapper.toEventShortDto(event, views);
                })
                .collect(Collectors.toSet());
    }
}
