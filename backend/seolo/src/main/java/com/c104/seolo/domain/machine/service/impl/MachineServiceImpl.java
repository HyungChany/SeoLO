package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.machine.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineServiceImpl {
    private final MachineRepository machineRepository;

}
