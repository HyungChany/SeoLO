package com.c104.seolo.domain.marker.service;

import com.c104.seolo.domain.marker.dto.MarkerDto;
import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;
import com.c104.seolo.domain.marker.dto.response.MarkerInfoResponse;

import java.util.List;

public interface MarkerService {

    void enrollMarker(List<AddMarkerRequest> markerRequests);
    MarkerInfoResponse getMarkerInfoById(Long markerId);
    MarkerDto getMarker(Long markerId);
}
