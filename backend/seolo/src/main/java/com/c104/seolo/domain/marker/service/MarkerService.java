package com.c104.seolo.domain.marker.service;

import com.c104.seolo.domain.marker.dto.request.AddMarkerRequest;

import java.util.List;

public interface MarkerService {

    void enrollMarker(List<AddMarkerRequest> markerRequests);
}
