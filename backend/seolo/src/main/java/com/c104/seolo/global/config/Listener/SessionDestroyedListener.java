package com.c104.seolo.global.config.Listener;

import com.c104.seolo.domain.alarm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionDestroyedListener implements ApplicationListener<HttpSessionDestroyedEvent> {
    private final NotificationService notificationService;

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        notificationService.removeEmitter(event.getId());
    }
}
