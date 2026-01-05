package ru.practicum.service.public_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.request.StatHitRequestDto;
import ru.practicum.dto.response.HitsCounterResponseDto;
import ru.practicum.service.admin_ewm.statistics.StatisticsService;
import ru.practicum.service.dal.CompilationRepository;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.dto.Constant;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.mapper.CompilationMapper;
import ru.practicum.service.mapper.EventMapper;
import ru.practicum.service.model.Compilation;
import ru.practicum.service.model.Event;
import ru.practicum.service.public_ewm.mapper.PublicEventMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    final CompilationRepository compilationRepository;
    final StatClient statClient;
    final StatisticsService statisticsService;
    private final String URI_EVENT_ENDPOINT = "/events/";

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size, HttpServletRequest request) {
        log.info("PublicCompilationService: выгрузка подборок по заданным параметрам");
        List<Compilation> compilationsList = compilationRepository.findCompilations(pinned, from, size);
        log.info("{}", compilationsList);

        Set<String> eventUris = compilationsList.stream()
                .flatMap(comp -> comp.getEvents().stream())
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toSet());

        List<HitsCounterResponseDto> stats = statClient.getHits(eventUris.stream().toList(), false);

        Map<Long, Long> eventViews = stats.stream()
                .collect(Collectors.toMap(
                        dto -> PublicEventMapper.extractIdFromUri(dto.getUri()),
                        HitsCounterResponseDto::getHits)
                );

        List<CompilationDto> result = new ArrayList<>();
        for (Compilation comp : compilationsList) {
            Set<EventShortDto> eventDtos = comp.getEvents().stream()
                    .map(event -> EventMapper.toEventShortDto(event, eventViews.getOrDefault(event.getId(), 0L)))
                    .collect(Collectors.toSet());

            CompilationDto compilationDto = CompilationMapper.toCompilationDto(comp, eventDtos);

            result.add(compilationDto);
        }

        statClient.hit(new StatHitRequestDto(Constant.SERVICE_POSTFIX,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT)))
        );

        return result;
    }

    @Override
    public CompilationDto getCompilationById(Long compId, HttpServletRequest request) {
        log.info("PublicCompilationService: поиск подборки с id: {}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка с id: %d не найдена", compId)));

        Set<EventShortDto> eventShortDtoList = getEventShortDto(compilation.getEvents());

        statClient.hit(new StatHitRequestDto(Constant.SERVICE_POSTFIX,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT)))
        );

        return CompilationMapper.toCompilationDto(compilation, eventShortDtoList);
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
