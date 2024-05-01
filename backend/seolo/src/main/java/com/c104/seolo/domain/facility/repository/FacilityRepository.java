package com.c104.seolo.domain.facility.repository;

import com.c104.seolo.domain.facility.dto.info.FacilityInfo;
import com.c104.seolo.domain.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query("select new com.c104.seolo.domain.facility.dto.info.FacilityInfo( " +
            "f.id, f.facilityName, f.facilityAddress, f.facilityLayout, f.facilityThum ) from Facility f " +
            "where f.company.companyCode = :company_code")
    List<FacilityInfo> getFacilities(String company_code);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Facility f " +
            "WHERE f.company.companyCode = :company_code")
    boolean existsByCompany(@Param("company_code") String company_code);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Facility f " +
            "WHERE f.facilityName = :facility_name")
    boolean existsFacilitiesByName(@Param("facility_name") String facility_name);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Facility f " +
            "WHERE f.facilityName = :facility_name " +
            "AND f.company.companyCode = :company_code " +
            "AND f.id <> :facility_id")
    boolean existsByNameAndCompanyCodeNot(@Param("facility_name") String facilityName,
                                          @Param("company_code") String companyCode,
                                          @Param("facility_id") Long facilityId);
}
