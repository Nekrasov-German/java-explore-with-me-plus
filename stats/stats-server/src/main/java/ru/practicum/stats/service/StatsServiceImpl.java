package ru.practicum.stats.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dal.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@NoArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private StatsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public void saveHit(
            HitDto dto
    ) {
       // to Entity mapper
        repository.save(dto);
    }

    @Override
    public List<VievsDto> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique
    ) {
        if (unique) {
            return repository.saveStatsUnique(start, end, uris);
        } else {
            return repository.getStats(start, end, uris);
        }
    }
}
