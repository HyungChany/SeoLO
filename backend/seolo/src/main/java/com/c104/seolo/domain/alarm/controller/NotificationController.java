package com.c104.seolo.domain.alarm.controller;

import com.c104.seolo.domain.alarm.dto.request.NotificationSendRequest;
import com.c104.seolo.domain.alarm.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class NotificationController {
    private final NotificationService notificationService;

    //응답 mime type 은 반드시 text/event-stream 이여야 한다.
    //클라이언트로 부터 SSE subscription 을 수락한다.
    @Secured("ROLE_MANAGER")
    @GetMapping(path = "/pub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(HttpSession session) {
        return notificationService.subscribe(session);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/send")
    public void sendMessage(@RequestBody NotificationSendRequest sendRequest) {
        notificationService.sendAsync(sendRequest);
    }
}
