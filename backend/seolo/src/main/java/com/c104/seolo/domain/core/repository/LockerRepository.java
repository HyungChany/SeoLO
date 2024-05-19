package com.c104.seolo.domain.core.repository;

import com.c104.seolo.domain.core.dto.info.LockerInfo;
import com.c104.seolo.domain.core.entity.Locker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LockerRepository extends CrudRepository<Locker, Long> {
    @Query("SELECT new com.c104.seolo.domain.core.dto.info.LockerInfo( " +
            "l.id, l.uid, l.isLocked, l.battery) FROM Locker l " +
            "WHERE l.company.companyCode = :companyCode")
    List<LockerInfo> findAllByCompanyCode(String companyCode);
    Optional<Locker> findByUid(String uid);

    @Query("SELECT COUNT(l) FROM Locker l WHERE l.company.companyCode = :companyCode")
    long countByCompanyCode(@Param("companyCode") String companyCode);
}
