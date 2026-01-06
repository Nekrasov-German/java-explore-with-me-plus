package ru.practicum.service.public_ewm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.service.dto.EventFullDto;
import ru.practicum.service.dto.EventSearchParams;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.public_ewm.service.PublicEventService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicEventController {
    final PublicEventService publicEventService;

//    @GetMapping
//    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false) String text,
//                                         @RequestParam(value = "categories", required = false) List<Long> categories,
//                                         @RequestParam(value = "paid", required = false) Boolean paid,
//                                         @RequestParam(value = "rangeStart", required = false)
//                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
//                                         @RequestParam(value = "rangeEnd", required = false)
//                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
//                                         @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
//                                         @RequestParam(value = "sort", required = false) EventSort sort,
//                                         @RequestParam(value = "from", defaultValue = "0") Integer from,
//                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
//                                         HttpServletRequest request) {
//        log.info("PublicEventController: вызов эндпоинта GET events/ " +
//                        "с параметрами запроса --  " +
//                        "text:{}, categories:{}, paid:{}, rangeStart:{}, rangeEnd:{}, onlyAvailable:{}, sort:{}, from:{}, size:{}",
//                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
//
//        return publicEventService.getEvents(text, categories, paid, rangeStart, rangeEnd,
//                onlyAvailable, sort, from, size, request);
//    }

    @GetMapping
    public List<EventShortDto> getEvents(@Valid EventSearchParams params,
                                         HttpServletRequest request) {
        log.info("PublicEventController: вызов эндпоинта GET events/ " +
                 "с параметрами запроса --  " +
                 "text:{}, categories:{}, paid:{}, rangeStart:{}, rangeEnd:{}, onlyAvailable:{}, sort:{}, from:{}, size:{}",
                params.getText(), params.getCategories(), params.getPaid(), params.getRangeStart(), params.getRangeEnd(),
                params.getOnlyAvailable(), params.getSort(), params.getFrom(), params.getSize());

        return publicEventService.getEvents(params.getText(),
                params.getCategories(),
                params.getPaid(),
                params.getRangeStart(),
                params.getRangeEnd(),
                params.getOnlyAvailable(),
                params.getSort(),
                params.getFrom(),
                params.getSize(),
                request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        log.info("PublicEventController: вызов эндпоинта GET events/{}", id);

        return publicEventService.getById(id, request);
    }
}
