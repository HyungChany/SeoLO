package com.c104.seolo.domain.machine.repository;

import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {
    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineListInfo( " +
            "f.id, f.facilityName, " +
            "m.machineSubcategory.subcategory, " +
            "m.id, m.name, m.number, m.createdAt, " +
            "mm.user.id, mm.user.employee.employeeName, mm.mm_role " +
            ") FROM Machine m " +
            "JOIN m.facility f " +
            "LEFT JOIN MachineManager mm ON m.id = mm.machine.id " +
            "WHERE f.id = :facilityId AND f.company.companyCode = :companyCode")
    Optional<List<MachineListInfo>> getMachinesByFacilityIdAndCompany(Long facilityId, String companyCode);
}
