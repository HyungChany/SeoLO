package com.c104.seolo.domain.core.entity;

import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "locker")
public class Locker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
    private Company company;

    @Column(name = "locker_isLocked")
    private Boolean isLocked;

    protected Locker () {}
}
