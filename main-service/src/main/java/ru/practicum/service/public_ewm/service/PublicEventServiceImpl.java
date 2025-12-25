package ru.practicum.service.public_ewm.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.response.HitsCounterResponseDto;
import ru.practicum.service.dal.EventRepository;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.dto.EventSort;
import ru.practicum.service.model.Event;
import ru.practicum.service.public_ewm.mapper.PublicEventMapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    final EventRepository eventRepository;
    final StatClient statClient;

    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         EventSort sort,
                                         Integer from,
                                         Integer size,
                                         HttpServletRequest request) {
        if (rangeStart == null && rangeEnd == null) rangeStart = LocalDateTime.now();
        if (rangeEnd == null) rangeEnd = LocalDateTime.now().plusYears(1000);

        List<Event> eventsList = eventRepository.findPublicEventsNative(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);
        List<String> eventsUrisList = eventsList.stream().map(event -> "/events/" + event.getId()).toList();

        List<HitsCounterResponseDto> hitsCounterList = statClient.getHits(rangeStart, rangeEnd, eventsUrisList, true);
        Map<Long, Long> eventIdEventHits =  hitsCounterList.stream()
                .collect(Collectors.toMap(hitsCounter ->
                        PublicEventMapper.extractIdFromUri(hitsCounter.getUri()), HitsCounterResponseDto::getHits));

        List<EventShortDto> result = eventsList.stream()
                .map(event -> PublicEventMapper.toEventShortDto(event, eventIdEventHits.getOrDefault(event.getId(), 0L)))
                .toList();

        if (sort == EventSort.VIEWS) result = result.stream()
                .sorted(Comparator.comparingLong(EventShortDto::getViews)
                        .reversed()).toList();

        return result;
    }
}
