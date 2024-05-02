package com.c104.seolo.domain.machine.entity;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.machine.enums.LockerType;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "machine")
public class Machine extends BaseEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "machine_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_subcategory", nullable = false)
    private MachineSubcategory machineSubcategory;

    @Column(name = "machine_name", length = 30, nullable = false)
    private String name;

    @Column(name = "machine_number", length = 30, nullable = false)
    private String number;

    @Column(name = "machine_longitude", nullable = false, columnDefinition = "INTEGER DEFAULT -1")
    private Float longitude;

    @Column(name = "machine_latitude", nullable = false, columnDefinition = "INTEGER DEFAULT -1")
    private Float latitude;

    @Column(name = "machine_company", length = 50, nullable = false)
    private String company;

    @Column(name = "machine_thum")
    private String thum;

    @Column(name = "machine_locker_type", length = 30, nullable = false)
    private LockerType lockerType;

    @Builder
    private Machine(Long id, Facility facility, MachineSubcategory machineSubcategory, String name, String number, Float longitude, Float latitude, LockerType lockerType) {
        this.id = id;
        this.facility = facility;
        this.machineSubcategory = machineSubcategory;
        this.name = name;
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lockerType = lockerType;
    }

    public Machine() {}
}
