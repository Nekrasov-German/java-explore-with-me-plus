package ru.practicum.service.public_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.request.StatHitRequestDto;
import ru.practicum.service.dal.CompilationRepository;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.dto.Constant;
import ru.practicum.service.error.NotFoundException;
import ru.practicum.service.mapper.CompilationMapper;
import ru.practicum.service.model.Compilation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    final CompilationRepository compilationRepository;
    final StatClient statClient;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size, HttpServletRequest request) {
        log.info("PublicCompilationService: выгрузка подборок по заданным параметрам");
        List<Compilation> compilationsList = compilationRepository.findCompilations(pinned, from, size);
        log.info("{}", compilationsList);

        statClient.hit(new StatHitRequestDto(Constant.SERVICE_POSTFIX,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT)))
        );

        return compilationsList.stream().map(CompilationMapper::toCompilationDto).toList();
    }

    @Override
    public CompilationDto getCompilationById(Long compId, HttpServletRequest request) {
        log.info("PublicCompilationService: поиск подпорки с id: {}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Подпорка с id: %d не найдена", compId)));

        statClient.hit(new StatHitRequestDto(Constant.SERVICE_POSTFIX,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT)))
        );

        return CompilationMapper.toCompilationDto(compilation);
    }
}
