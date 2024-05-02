package com.c104.seolo.domain.machine.repository;

import com.c104.seolo.domain.machine.entity.MachineManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineManagerRepository extends JpaRepository<MachineManager, Long> {
}
