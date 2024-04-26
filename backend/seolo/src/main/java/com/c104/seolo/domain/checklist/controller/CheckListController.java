package com.c104.seolo.domain.checklist.controller;

import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{check_list_id}")
    public ResponseEntity<Void> deleteCheckList(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable("check_list_id") Long checkListId
    ) {
        checkListService.deleteCheckListByCompany(companyCode, checkListId);
        return ResponseEntity.noContent().build();
    }
}
