package ru.practicum.service.private_ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.dto.ParticipationRequestDto;
import ru.practicum.service.private_ewm.service.PrivateService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestsController {
    private final PrivateService service;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getInfoOnParticipation(
            @PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok().body(service.getInfoOnParticipation(userId));
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createRequestForParticipation(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "eventId") Long eventId) {
//        Обратите внимание:
//
//        нельзя добавить повторный запрос (Ожидается код ошибки 409)
//        инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
//        нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
//        если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
//        если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRequestForParticipation(userId, eventId));
    }

    @PatchMapping
    public ResponseEntity<ParticipationRequestDto> canceledRequestForParticipation(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "requestId") Long requestId) {
        return ResponseEntity.ok().body(service.canceledRequestForParticipation(userId, requestId));
    }
}
