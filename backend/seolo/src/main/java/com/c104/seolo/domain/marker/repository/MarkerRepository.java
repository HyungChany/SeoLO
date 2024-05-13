package com.c104.seolo.domain.marker.repository;

import com.c104.seolo.domain.marker.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    List<Marker> findAllByFacilityId(Long facilityId);
}
