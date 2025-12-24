package ru.practicum.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class StatsClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader("User-Agent", "Stats-Client/1.0")
                .build();
    }
}
