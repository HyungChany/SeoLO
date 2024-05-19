package com.c104.seolo.domain.core.repository;

import com.c104.seolo.domain.core.entity.LockerAccessLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerAccessLogRepository extends CrudRepository<LockerAccessLog, Long> {
}
