package ru.practicum.service.public_ewm.mapper;

import ru.practicum.service.dto.*;
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

    public static EventFullDto toEventFullDto(Event e, Long views) {
        EventFullDto efd = new EventFullDto();
        efd.setAnnotation(e.getAnnotation());

        CategoryDto cd = new CategoryDto(); // Заменить на маппер
        cd.setId(e.getCategory().getId());
        cd.setName(e.getCategory().getName());
        efd.setCategoryDto(cd);

        efd.setConfirmedRequests(e.getConfirmedRequests());
        efd.setCreatedOn(e.getCreatedOn());
        efd.setDescription(e.getDescription());
        efd.setEventDate(e.getEventDate());
        efd.setId(e.getId());

        UserShortDto usd = new UserShortDto(); // Заменить на маппер
        usd.setId(e.getInitiator().getId());
        usd.setName(e.getInitiator().getName());
        efd.setInitiatorDto(usd);

        LocationDto ld = new LocationDto(); // Заменить на маппер
        ld.setLat(e.getLocation().getLat());
        ld.setLon(e.getLocation().getLon());
        efd.setLocationDto(ld);

        efd.setPaid(e.getPaid());
        efd.setParticipantLimit(e.getParticipantLimit());
        efd.setPublishedOn(e.getPublishedOn());
        efd.setRequestModeration(e.getRequestModeration());
        efd.setState(e.getState());
        efd.setTitle(e.getTitle());
        efd.setViews(views);

        return efd;
    }

    public static Long extractIdFromUri(String uri) {
        // формат "/events/{id}"
        String[] parts = uri.split("/");
        String id = parts[parts.length - 1];
        return Long.parseLong(id);
    }
}
