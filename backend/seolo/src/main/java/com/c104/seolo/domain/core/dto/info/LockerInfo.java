package com.c104.seolo.domain.core.dto.info;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LockerInfo {
    private Long id;
    private String uid;
    private Boolean locked;
    private Integer battery;
}
