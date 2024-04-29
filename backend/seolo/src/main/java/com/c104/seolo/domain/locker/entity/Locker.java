package com.c104.seolo.domain.locker.entity;

import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "locker")
public class Locker {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "locker_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_code", nullable = false)
    private Company company;

    @Column(name = "locker_uid", length = 8, nullable = false)
    private String uid;

    @Column(name = "locker_isLocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isLocked;

    @Column(name = "locker_battery", nullable = false, columnDefinition = "INT DEFAULT 100")
    private int battery;

}
