package com.c104.seolo.domain.marker.controller;

import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;
import com.c104.seolo.domain.marker.dto.response.MarkerInfoResponse;
import com.c104.seolo.domain.marker.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/markers")
public class MarkerController {
    private final MarkerService markerService;

    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_MANAGER")
    @PostMapping()
    public void receiveMarkerData(@RequestBody List<AddMarkerRequest> markerRequests) {
        markerService.enrollMarker(markerRequests);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_MANAGER")
    @GetMapping("/{markerId}")
    public MarkerInfoResponse receiveMarkerData(@PathVariable Long markerId) {
        return markerService.getMarkerInfoById(markerId);
    }
}
