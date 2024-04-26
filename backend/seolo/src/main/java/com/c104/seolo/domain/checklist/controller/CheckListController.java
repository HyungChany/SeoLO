package com.c104.seolo.domain.checklist.controller;

import com.c104.seolo.domain.checklist.dto.request.CheckListRequest;
import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("checklist")
@RequiredArgsConstructor
@Slf4j
public class CheckListController {
    private final CheckListService checkListService;

    @GetMapping()
    public ResponseEntity<GetCheckListResponse> getAllCheckLists(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(checkListService.getCheckListByCompany(companyCode));
    }

    @PostMapping()
    public ResponseEntity<Void> createCheckList(
            @RequestHeader("Company-Code") String companyCode,
            @RequestBody CheckListRequest checkListRequest
            ) {
        checkListService.createCheckList(checkListRequest, companyCode);
        URI location = URI.create("/checklist");
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{check_list_id}")
    public ResponseEntity<Void> updateCheckList(
            @RequestHeader("Company-Code") String companyCode,
            @RequestBody CheckListRequest checkListRequest,
            @PathVariable("check_list_id") Long checkListId
    ) {
        checkListService.updateCheckList(checkListRequest, checkListId, companyCode);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{check_list_id}")
    public ResponseEntity<Void> deleteCheckList(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable("check_list_id") Long checkListId
    ) {
        checkListService.deleteCheckListByCompany(companyCode, checkListId);
        return ResponseEntity.noContent().build();
    }
}
