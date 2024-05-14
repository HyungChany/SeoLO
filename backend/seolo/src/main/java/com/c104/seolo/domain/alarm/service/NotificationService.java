package com.c104.seolo.domain.alarm.service;

import com.c104.seolo.domain.alarm.dto.request.NotificationSendRequest;
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
    private static final long TIMEOUT = 10*60*1000;
    private static final long HEARTBEAT_INTERVAL = 10 * 1000L; // 30초마다 하트비트 전송

    public SseEmitter subscribe(Long userId) {
        log.info("SSE 구독 요청 시작: {} (스레드: {})", userId, Thread.currentThread().getName());
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(userId.toString(), emitter);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitterMap.remove(userId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });


        log.info("SSE 구독 요청 완료: {} (스레드: {})", userId, Thread.currentThread().getName());
        return emitter;
    }

    @Async("notiExecutor")
    public void sendAsync(NotificationSendRequest sendRequest) {
        // emitterMap의 모든 사용자에게 메시지 전송
        emitterMap.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data(sendRequest)); // 모든 사용자에게 메시지 전송
                log.info("서버로부터 SSE 전송 성공: 사용자 {}, 데이터 {}", userId, sendRequest);
            } catch (IOException e) {
                log.error("SSE 전송 실패: 사용자 {}, 오류 메시지 {}", userId, e.getMessage(), e);
                emitter.completeWithError(e);
                emitterMap.remove(userId);
            }
        });
        log.info("비동기 메서드 종료 (스레드: {})", Thread.currentThread().getName());
    }
    // 하트비트 전송을 위한 메소드 추가
    @Scheduled(fixedRate = HEARTBEAT_INTERVAL)
    public void sendHeartbeat() {
        emitterMap.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data("heartbeat"));
//                log.info("Heartbeat sent to {}", userId);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitterMap.remove(userId);
            }
        });
    }
}
