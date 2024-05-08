package com.c104.seolo.domain.machine.dto;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.machine.entity.MachineSubcategory;
import com.c104.seolo.domain.machine.enums.LockerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MachineDto {
    private Long id;
    private Facility facility;
    private MachineSubcategory machineSubcategory;
    private String name;
    private String number;
    private Float longitude;
    private Float latitude;
    private String company;
    private String thum;
    private LockerType lockerType;
    private Date introductionDate;

    @Builder
    public MachineDto(Long id, Facility facility, MachineSubcategory machineSubcategory, String name, String number, Float longitude, Float latitude, String company, String thum, LockerType lockerType, Date introductionDate) {
        this.id = id;
        this.facility = facility;
        this.machineSubcategory = machineSubcategory;
        this.name = name;
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
        this.company = company;
        this.thum = thum;
        this.lockerType = lockerType;
        this.introductionDate = introductionDate;
    }

    public static MachineDto of(Machine machine) {
        return MachineDto.builder()
                .id(machine.getId())
                .facility(machine.getFacility())
                .machineSubcategory(machine.getMachineSubcategory())
                .name(machine.getName())
                .number(machine.getNumber())
                .longitude(machine.getLongitude())
                .latitude(machine.getLatitude())
                .company(machine.getCompany())
                .thum(machine.getThum())
                .lockerType(machine.getLockerType())
                .introductionDate(machine.getIntroductionDate())
                .build();
    }

    public Machine toEntity() {
        return Machine.builder()
                .id(id)
                .facility(facility)
                .machineSubcategory(machineSubcategory)
                .name(name)
                .number(number)
                .longitude(longitude)
                .latitude(latitude)
                .company(company)
                .thum(thum)
                .lockerType(lockerType)
                .introductionDate(introductionDate)
                .build();
    }
}