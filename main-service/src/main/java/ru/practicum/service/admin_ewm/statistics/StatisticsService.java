package ru.practicum.service.admin_ewm.statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    Map<String, Long> getViewsByUris(List<String> uris, boolean unique);

    Long getViewsByUri(String uri, boolean unique);
}
