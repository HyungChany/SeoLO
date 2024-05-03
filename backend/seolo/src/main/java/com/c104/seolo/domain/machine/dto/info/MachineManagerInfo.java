package com.c104.seolo.domain.machine.dto.info;

import com.c104.seolo.domain.machine.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MachineManagerInfo {
    private Long id;
    private Long machineManagerId;
    private String name;
    private Role role;
}
