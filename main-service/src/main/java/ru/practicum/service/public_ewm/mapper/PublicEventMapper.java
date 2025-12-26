package ru.practicum.service.public_ewm.mapper;

import ru.practicum.service.dto.CategoryDto;
import ru.practicum.service.dto.EventShortDto;
import ru.practicum.service.dto.UserShortDto;
import ru.practicum.service.model.Event;

public class PublicEventMapper {
    public static EventShortDto toEventShortDto(Event e, Long views) {
        EventShortDto esd = new EventShortDto();
        esd.setId(e.getId());
        esd.setAnnotation(e.getAnnotation());

        CategoryDto cd = new CategoryDto();
        cd.setId(e.getCategory().getId()); // Заменить на
        cd.setName(e.getCategory().getName()); // маппер
        esd.setCategoryDto(cd);

        esd.setConfirmedRequests(e.getConfirmedRequests());
        esd.setEventDate(e.getEventDate());

        UserShortDto usd = new UserShortDto();
        usd.setId(e.getInitiator().getId()); // Заменить на
        usd.setName(e.getInitiator().getName()); // маппер
        esd.setInitiatorDto(usd);

        esd.setPaid(e.getPaid());
        esd.setTitle(e.getTitle());
        esd.setViews(views);

        return esd;
    }

    public static Long extractIdFromUri(String uri) {
        // формат "/events/{id}"
        String[] parts = uri.split("/");
        String id = parts[parts.length - 1];
        return Long.parseLong(id);
    }
}
