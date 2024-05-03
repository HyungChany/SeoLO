package com.c104.seolo.domain.machine.repository;

import com.c104.seolo.domain.machine.dto.info.MachineManagerInfo;
import com.c104.seolo.domain.machine.entity.MachineManager;
import com.c104.seolo.domain.machine.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MachineManagerRepository extends JpaRepository<MachineManager, Long> {
    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineManagerInfo( " +
            "mm.id, mm.user.id, mm.user.employee.employeeName, mm.mm_role " +
            ") FROM MachineManager mm " +
            "WHERE mm.machine.id = :machineId AND mm.mm_role = :role")
    Optional<MachineManagerInfo> findMachineManagerInfoByMachineIdAndRole(Long machineId, Role role);

    @Query("SELECT mm from MachineManager mm " +
            "where mm.machine.id = :machineId AND mm.mm_role = :role ")
    MachineManager findMachineManagerByMachineIdAndRole(Long machineId, Role role);
}
