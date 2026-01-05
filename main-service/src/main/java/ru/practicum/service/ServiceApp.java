package ru.practicum.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import ru.practicum.client.StatClient;
import ru.practicum.dto.request.StatHitRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        StatHitRequestDto statHitRequestDto2 = StatHitRequestDto
                .builder()
                .app("ewm-main-service")
                .uri("/user/1")
                .ip("192.163.0.1")
                .timestamp("2025-09-06 11:00:23")
                .build();

        ResponseEntity<Void> answer = client.hit(statHitRequestDto);
        ResponseEntity<Void> answer2 = client.hit(statHitRequestDto2);

        System.out.println(answer);

        List<String> request = new ArrayList<>();
        request.add("/events/1");
        request.add("/user/1");

        String stats = client.getHits(
                request,
                false
        ).toString();

        System.out.println(stats);
    }
}
