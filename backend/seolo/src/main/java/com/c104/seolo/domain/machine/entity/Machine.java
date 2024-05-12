package com.c104.seolo.domain.machine.entity;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.machine.enums.LockerType;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_subcategory")
    private MachineSubcategory machineSubcategory;

    @Column(name = "machine_name", length = 30, nullable = false)
    private String name;

    @Column(name = "machine_number", length = 30, nullable = false)
    private String number;

    @Column(name = "machine_company", length = 50)
    private String company;

    @Column(name = "machine_thum")
    private String thum;

    @Column(name = "machine_locker_type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private LockerType lockerType;

    @Column(name="machine_introduction_date", nullable = false)
    private Date introductionDate;

    @Builder
    private Machine(Long id, Facility facility, MachineSubcategory machineSubcategory, String name, String number, String company, String thum, LockerType lockerType, Date introductionDate) {
        this.id = id;
        this.facility = facility;
        this.machineSubcategory = machineSubcategory;
        this.name = name;
        this.number = number;
        this.company = company;
        this.thum = thum;
        this.lockerType = lockerType;
        this.introductionDate = introductionDate;
    }

    public Machine() {}

}
