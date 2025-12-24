package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.practicum.dto.request.StatHitRequestDto;
import ru.practicum.dto.response.HitsCounterResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatClient {
    private final RestClient restClient;

    private static final String HIT_ENDPOINT = "/hit";
    private static final String STATS_ENDPOINT = "/stats";

    public ResponseEntity<Void> hit(StatHitRequestDto dto) {
        return restClient.post()
                .uri(HIT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

        public List<HitsCounterResponseDto> getHits(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    ) {
        return restClient.get()
                .uri(STATS_ENDPOINT +
                                "?start={start}&end={end}&unique={unique}" +
                                (uris != null ? "&uris={uris}" : ""),
                        start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        unique,
                        uris
                )
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<HitsCounterResponseDto>>() {});
    }
}
