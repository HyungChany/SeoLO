package com.c104.seolo.domain.machine.entity;

import com.c104.seolo.domain.machine.enums.LockerType;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
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
    @JoinColumn(name = "machine_subcategory", nullable = false)
    private MachineSubcategory machineSubcategory;

    @Column(name = "machine_name", length = 30, nullable = false)
    private String name;

    @Column(name = "machine_number", length = 30, nullable = false)
    private String number;

    @Column(name = "machine_longitude", nullable = false)
    private Float longitude;

    @Column(name = "machine_latitude", nullable = false)
    private Float latitude;

    @Column(name = "machine_company", length = 50, nullable = false)
    private String company;

    @Column(name = "machine_thum")
    private String thum;

    @Column(name = "machine_locker_type", length = 30, nullable = false)
    private LockerType lockerType;
}
