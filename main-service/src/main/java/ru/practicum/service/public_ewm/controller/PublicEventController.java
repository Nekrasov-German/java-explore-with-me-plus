package ru.practicum.service.public_ewm.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.dto.EventFullDto;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.dto.EventSort;
import ru.practicum.service.public_ewm.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicEventController {
    final PublicEventService publicEventService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(name = "sort", required = false) EventSort sort,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        log.info("PublicEventController: вызов эндпоинта GET events/ " +
                        "с параметрами запроса --  " +
                        "text:{}, categories:{}, paid:{}, rangeStart:{}, rangeEnd:{}, onlyAvailable:{}, sort:{}, from:{}, size:{}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        return publicEventService.getEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        log.info("PublicEventController: вызов эндпоинта GET events/{}", id);

        return publicEventService.getById(id, request);
    }
}
