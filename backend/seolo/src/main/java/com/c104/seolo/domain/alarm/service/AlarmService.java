//package com.c104.seolo.domain.alarm.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Slice;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class AlarmService {
//
//
//    // [알림함] 사용자가 받은 알림 조회
//    public List<MyEventAlarmResponse> getEventAlarmsForUser() {
//        // 첫 페이지, 페이지 당 30개 항목으로 페이징 설정
//        Slice<EventAlarm> eventAlarmSlice = eventAlarmRepository.
//                findByReceiveUserId(
//                        userService.getUserInfo().getId(),
//                        PageRequest.of(0, 10)
//                );
//        log.info("알람 요청자 : {} ", userService.getUserInfo().getId());
//        log.info("내 알람리스트 : {}", eventAlarmSlice.toString());
//        // 조회된 이벤트 알람 리스트 반환
//
//        return eventAlarmSlice.getContent().stream().map(eventAlarm ->
//                MyEventAlarmResponse.builder()
//                        .alarmId(eventAlarm.getAlarmId())
//                        .alarmType(eventAlarm.getType().toString())
//                        .alarmContent(eventAlarm.getContent())
//                        .alarmRedirect(eventAlarm.getRedirect())
//                        .isChecked(eventAlarm.isChecked())
//                        .build()
//        ).collect(Collectors.toList());
//    }
//}
