package ru.practicum.stats.service;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void saveHit(HitDto dto);

    List<VievsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
