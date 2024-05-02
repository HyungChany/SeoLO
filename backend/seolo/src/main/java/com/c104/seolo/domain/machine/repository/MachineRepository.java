package com.c104.seolo.domain.machine.repository;

import com.c104.seolo.domain.machine.dto.info.MachineInfo;
import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {
    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineListInfo( " +
            "f.id, f.facilityName, " +
            "sc.subcategory, " +
            "m.id, m.name, m.number, m.introductionDate, " +
            "CASE WHEN mm.mm_role = com.c104.seolo.domain.machine.enums.Role.Main THEN mm.user.id ELSE null END, " +
            "CASE WHEN mm.mm_role = com.c104.seolo.domain.machine.enums.Role.Main THEN mm.user.employee.employeeName ELSE null END, " +
            "CASE WHEN mm.mm_role = com.c104.seolo.domain.machine.enums.Role.Sub THEN mm.user.id ELSE null END, " +
            "CASE WHEN mm.mm_role = com.c104.seolo.domain.machine.enums.Role.Sub THEN mm.user.employee.employeeName ELSE null END " +
            ") FROM Machine m " +
            "JOIN m.machineSubcategory sc " +
            "JOIN m.facility f " +
            "LEFT JOIN MachineManager mm ON m.id = mm.machine.id " +
            "WHERE f.id = :facilityId AND f.company.companyCode = :companyCode")
    Optional<List<MachineListInfo>> getMachinesByFacilityIdAndCompany(@Param("facilityId")Long facilityId, @Param("companyCode") String companyCode);

    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineInfo( " +
            "m.id, m.facility.company.companyCode, m.facility.id, m.facility.facilityName, m.name, m.number, m.thum, m.introductionDate " +
            ") FROM Machine m LEFT JOIN MachineManager mm on m.id = mm.machine.id " +
            "WHERE m.id = :machineId " +
            "order by m.id desc limit 1 ")
    Optional<MachineInfo> findById(@Param("machineId") Long machineId);
}
