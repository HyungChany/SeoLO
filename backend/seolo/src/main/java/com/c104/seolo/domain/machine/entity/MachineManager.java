package com.c104.seolo.domain.machine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "machine_manager")
public class MachineManager {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "machine_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")

    @Column(name = "mm_role", length = 9)
    private
}
