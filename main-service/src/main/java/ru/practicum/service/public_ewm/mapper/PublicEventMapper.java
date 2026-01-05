package ru.practicum.service.public_ewm.mapper;

import ru.practicum.service.dto.*;
import ru.practicum.service.model.Event;

public class PublicEventMapper {
    public static Long extractIdFromUri(String uri) {
        // формат "/events/{id}"
        String[] parts = uri.split("/");
        String id = parts[parts.length - 1];
        return Long.parseLong(id);
    }
}
