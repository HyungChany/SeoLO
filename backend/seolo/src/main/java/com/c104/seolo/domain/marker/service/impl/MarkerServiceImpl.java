package com.c104.seolo.domain.marker.service.impl;

import com.c104.seolo.domain.facility.dto.response.FacilityBlueprintResponse;
import com.c104.seolo.domain.facility.service.FacilityService;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.marker.dto.MarkerDto;
import com.c104.seolo.domain.marker.dto.MarkerLocation;
import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;
import com.c104.seolo.domain.marker.dto.response.MarkerInfoResponse;
import com.c104.seolo.domain.marker.dto.response.MarkerLocationResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository markerRepository;
    private final MachineService machineService;
    private final TaskHistoryService taskHistoryService;
    private final DBUserDetailService dbUserDetailService;
    private final FacilityService facilityService;

    @Override
    @Transactional
    public void enrollMarker(AddMarkerRequest request) {
        MachineDto machine = machineService.getMachineByMachineId(request.getMachineId());

        Marker newMarker = Marker.builder()
                .facility(machine.getFacility())
                .machine(machine.toEntity())
                .locationX(request.getMarkerX())
                .locationY(request.getMarkerY())
                .build();

        markerRepository.save(newMarker);

    }

    @Override
    @Transactional
    public void enrollMarkers(List<AddMarkerRequest> markerRequests) {
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
            MachineDto machine = machineService.getMachineByMachineId(marker.getMachineId());

            return MarkerInfoResponse.builder()
                    .machineNum(machine.getNumber())
                    .machineName(machine.getName())
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

    @Override
    public List<MarkerDto> getAllMarkersByFacilityId(Long facilityId) {
        return markerRepository.findAllByFacilityId(facilityId).stream()
                .map(MarkerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public FacilityBlueprintResponse getBlueprintAndMarkerByFacilityId(Long facilityId) {
        List<MarkerDto> markers = getAllMarkersByFacilityId(facilityId);

        ArrayList<MarkerLocationResponse> locationResponses = new ArrayList<>();
        for (MarkerDto marker : markers) {
            MarkerLocation location = MarkerLocation.builder()
                    .locationX(marker.getLocationX())
                    .locationY(marker.getLocationY())
                    .build();

            locationResponses.add(MarkerLocationResponse.builder()
                    .markerId(marker.getId())
                    .markerLocations(location)
                    .build());
        }

        return FacilityBlueprintResponse.builder()
                    .blueprintURL(facilityService.getLayoutByFacility(facilityId))
                    .markers(locationResponses)
                    .build();
    }

    @Override
    public void deleteMarker(Long markerId) {
        Marker marker = markerRepository.findById(markerId).orElseThrow(
                () -> new CommonException(MarkerErrorCode.NOT_EXIST_MARKER)
        );

        markerRepository.delete(marker);
    }
}
