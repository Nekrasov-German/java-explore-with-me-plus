package ru.practicum.stats.service;

import request.StatHitRequestDto;
import response.HitsCounterResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void saveHit(StatHitRequestDto dto);

    List<HitsCounterResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
