package com.c104.seolo.domain.marker.service.impl;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.machine.exception.MachineErrorCode;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;
import com.c104.seolo.domain.marker.entity.Marker;
import com.c104.seolo.domain.marker.repository.MarkerRepository;
import com.c104.seolo.domain.marker.service.MarkerService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository markerRepository;
    private final MachineService machineService;

    @Override
    @Transactional
    public void enrollMarker(List<AddMarkerRequest> markerRequests) {
        for (AddMarkerRequest request : markerRequests) {
            MachineDto machine = machineService.getMachineByMachineId(request.getMachineId());

            Marker newMarker = Marker.builder()
                    .facility(machine.getFacility())
                    .machine(machine.toEntity())
                    .locationX(request.getMarkerX())
                    .locationY(request.getMarkerY())
                    .build();

            markerRepository.save(newMarker);
        }
    }
}
