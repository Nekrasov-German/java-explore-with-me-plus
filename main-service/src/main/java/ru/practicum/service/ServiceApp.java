package ru.practicum.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import ru.practicum.client.StatClient;
import ru.practicum.dto.request.StatHitRequestDto;

import java.time.LocalDateTime;

@SpringBootApplication
@ComponentScan({
        "ru.practicum.service",
        "ru.practicum.client",
        "ru.practicum.dto"
})
public class ServiceApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServiceApp.class, args);

        StatClient client = context.getBean(StatClient.class);

        StatHitRequestDto statHitRequestDto = StatHitRequestDto
                .builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2025-09-06 11:00:23")
                .build();

        ResponseEntity<Void> answer = client.hit(statHitRequestDto);

        System.out.println(answer);

        String stats = client.getHits(
                LocalDateTime.of(2024, 12, 20, 0, 0),
                LocalDateTime.of(2026, 12, 20, 0, 0),
                null,
                true
        ).toString();

        System.out.println(stats);
    }
}
