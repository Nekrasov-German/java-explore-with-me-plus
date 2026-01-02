package ru.practicum.service.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.dto.NewCompilationDto;
import ru.practicum.service.model.Compilation;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public Compilation newCompilationToEntity(NewCompilationDto dto) {
        return Compilation.builder()
                .title(dto.getTitle())
                .pinned(dto.getPinned())
                .events(Collections.emptySet())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        Set<EventShortDto> eventsDto = Collections.emptySet();

        if (compilation.getEvents() != null) {
            eventsDto = compilation.getEvents().stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toSet());
        }

        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventsDto)
                .build();
    }
}
