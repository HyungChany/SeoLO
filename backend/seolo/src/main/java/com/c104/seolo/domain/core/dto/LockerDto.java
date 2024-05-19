package com.c104.seolo.domain.core.dto;

import com.c104.seolo.domain.core.entity.Locker;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class LockerDto {
    private Long id;
    private String uid;
    private boolean isLocked;
    private Integer battery;
    private String encryptionKey;

    @Builder
    public LockerDto(Long id, String uid, boolean isLocked, Integer battery, String encryptionKey) {
        this.id = id;
        this.uid = uid;
        this.isLocked = isLocked;
        this.battery = battery;
        this.encryptionKey = encryptionKey;
    }

    public static LockerDto of(Locker locker) {
        return LockerDto.builder()
                .id(locker.getId())
                .uid(locker.getUid())
                .isLocked(locker.isLocked())
                .battery(locker.getBattery())
                .encryptionKey(locker.getEncryptionKey())
                .build();
    }

    public Locker toEntity() {
        return new Locker.Builder()
                .uid(uid)
                .isLocked(isLocked)
                .battery(battery)
                .encryptionKey(encryptionKey)
                .build();
    }
}
