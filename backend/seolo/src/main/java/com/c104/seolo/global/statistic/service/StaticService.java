package com.c104.seolo.global.statistic.service;

public interface StaticService {

    Long countAllMachinesByFacility(Long facilityId);
    Long countAllLockerByCompanyCode(String companyCode);
}
