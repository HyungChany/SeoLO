package com.c104.seolo.domain.machine.entity;

import com.c104.seolo.domain.machine.enums.Role;
import com.c104.seolo.domain.user.entity.AppUser;
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
@Table(name = "machine_manager")
public class MachineManager {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "machine_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "mm_role", length = 9)
    private Role mm_role;

    @Builder
    private MachineManager(Long id, Machine machine, AppUser user, Role mm_role) {
        this.id = id;
        this.machine = machine;
        this.user = user;
        this.mm_role = mm_role;
    }

    public MachineManager() {}
}
