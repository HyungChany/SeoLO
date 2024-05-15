package com.c104.seolo.domain.alarm.service;

import com.c104.seolo.domain.alarm.dto.request.NotificationSendRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class NotificationService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 10 * 60 * 1000;
    private static final long HEARTBEAT_INTERVAL = 20 * 1000L;

    public SseEmitter subscribe(HttpSession session) {
        String sessionId = session.getId();
        log.info("SSE 구독 요청 시작: {} (스레드: {})", sessionId, Thread.currentThread().getName());
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(sessionId, emitter);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback for session: {}", sessionId);
            emitterMap.remove(sessionId);
        });

        emitter.onTimeout(() -> {
            log.info("onTimeout callback for session: {}", sessionId);
            emitter.complete();
            emitterMap.remove(sessionId);
        });

        log.info("SSE 구독 요청 완료: {} (스레드: {})", sessionId, Thread.currentThread().getName());
        return emitter;
    }

    @Async("notiExecutor")
    public void sendAsync(NotificationSendRequest sendRequest) {
        emitterMap.forEach((sessionId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data(sendRequest)); // 모든 사용자에게 메시지 전송
                log.info("서버로부터 SSE 전송 성공: 세션 {}, 데이터 {}", sessionId, sendRequest);
            } catch (IOException e) {
                log.error("SSE 전송 실패: 세션 {}, 오류 메시지 {}", sessionId, e.getMessage(), e);
                emitter.completeWithError(e);
                emitterMap.remove(sessionId);
            }
        });
    }

    @Scheduled(fixedRate = HEARTBEAT_INTERVAL)
    public void sendHeartbeat() {
        emitterMap.forEach((sessionId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data("heartbeat"));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitterMap.remove(sessionId);
            }
        });
    }

    public void removeEmitter(String sessionId) {
        SseEmitter emitter = emitterMap.remove(sessionId);
        if (emitter != null) {
            emitter.complete();
        }
    }


}
