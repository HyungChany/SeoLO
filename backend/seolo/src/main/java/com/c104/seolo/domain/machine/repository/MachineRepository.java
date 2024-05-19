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
public interface MachineRepository extends JpaRepository<Machine, Long> {
    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineListInfo( " +
            "m.facility.id, m.facility.facilityName, " +
            "m.id, m.name, m.number, m.introductionDate " +
            ") FROM Machine m " +
            "WHERE m.facility.id = :facilityId AND m.facility.company.companyCode = :companyCode")
    Optional<List<MachineListInfo>> getMachinesByFacilityIdAndCompany(@Param("facilityId")Long facilityId, @Param("companyCode") String companyCode);

    @Query("SELECT new com.c104.seolo.domain.machine.dto.info.MachineInfo( " +
            "m.id, m.facility.company.companyCode, m.facility.id, m.facility.facilityName, m.name, m.number, m.thum, m.introductionDate " +
            ") FROM Machine m LEFT JOIN MachineManager mm on m.id = mm.machine.id " +
            "WHERE m.id = :machineId " +
            "order by m.id desc limit 1 ")
    Optional<MachineInfo> findInfoById(@Param("machineId") Long machineId);

    List<Machine> findByFacilityId(Long facilityId);

    @Query("SELECT COUNT(m) FROM Machine m WHERE m.facility.id = :facilityId")
    Long countByFacilityId(@Param("facilityId") Long facilityId);
}
