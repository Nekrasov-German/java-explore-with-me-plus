package ru.practicum.service.public_ewm.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.service.dto.CompilationDto;
import ru.practicum.service.public_ewm.service.PublicCompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/compilations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicCompilationController {
    PublicCompilationService compilationService;

    @GetMapping
    public CompilationDto getCompilationsByParam(@RequestParam(value = "pinned") Boolean pinned,
                                          @RequestParam(value = "from", defaultValue = "0") Integer from,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("PublicCompilationController: вызов эндпоинта GET /compilation с параметрами --" +
                " pinned: {}, from: {}, size: {}", pinned, from, size);

        return compilationService.findCompilations(pinned, from, size);
    }
}
