package com.c104.seolo.global.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static LocalDateTime getStartOfWeek(LocalDate date) {
        // 조회 하고자하는 날짜 week의 월요일 첫 시간 가져오기
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
    }

    public static LocalDateTime getEndOfWeek(LocalDate date) {
        // 조회 하고자하는 날짜 week의 일요일 마지막 시간 가져오기
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
    }

    public static LocalDateTime getStartOfMonth(LocalDate date) {
        // 조회 하고자하는 날짜 month의 첫날 첫 시간 가져오기
        return date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    }

    public static LocalDateTime getEndOfMonth(LocalDate date) {
        // 조회 하고자하는 날짜 month의 마지막날 마지막 시간 가져오기
        return date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
    }
}
