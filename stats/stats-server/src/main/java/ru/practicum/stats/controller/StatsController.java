package ru.practicum.stats.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@NoArgsConstructor
public class StatsController {
    private StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Void> saveHit(
            @RequestBody HitDto dto   // ------------------------------------------
    ) {
        statsService.saveHit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<List<VievsDto>> getStats(  // --------------------------------------------
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique
    ) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start, dtf);
        LocalDateTime endDate = LocalDateTime.parse(end, dtf);

        List<VievsDto> stats = statsService.getStats(startDate, endDate, uris, unique);

        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
