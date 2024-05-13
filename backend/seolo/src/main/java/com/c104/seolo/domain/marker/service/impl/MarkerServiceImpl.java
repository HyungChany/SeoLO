package com.c104.seolo.domain.marker.service.impl;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.marker.dto.MarkerDto;
import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;
import com.c104.seolo.domain.marker.dto.response.MarkerInfoResponse;
import com.c104.seolo.domain.marker.entity.Marker;
import com.c104.seolo.domain.marker.exception.MarkerErrorCode;
import com.c104.seolo.domain.marker.repository.MarkerRepository;
import com.c104.seolo.domain.marker.service.MarkerService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.service.DBUserDetailService;
import com.c104.seolo.global.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository markerRepository;
    private final MachineService machineService;
    private final TaskHistoryService taskHistoryService;
    private final DBUserDetailService dbUserDetailService;

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

    @Override
    public MarkerInfoResponse getMarkerInfoById(Long markerId) {

        try {
            MarkerDto marker = getMarker(markerId);
            TaskHistoryDto nowTask = taskHistoryService.getLatestTaskHistoryEntityByMachineId(marker.getMachineId());
            AppUser worker = dbUserDetailService.loadUserById(nowTask.getUserId());

            return MarkerInfoResponse.builder()
                    .workerName(worker.getUsername())
                    .estimatedEndTime(DateUtils.formatToLocalDateStr(nowTask.getTaskEndEstimatedDateTime()))
                    .content(nowTask.getTaskPrecaution())
                    .build();

        } catch (CommonException e) {
            return MarkerInfoResponse.builder()
                    .workerName(null)
                    .estimatedEndTime(null)
                    .content(null)
                    .build();
        }
    }

    @Override
    public MarkerDto getMarker(Long markerId) {
        Marker marker = markerRepository.findById(markerId).orElseThrow(
                () -> new CommonException(MarkerErrorCode.NOT_EXIST_MARKER));
        return MarkerDto.of(marker);
    }
}
