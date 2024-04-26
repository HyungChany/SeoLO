package com.c104.seolo.domain.checklist.controller;

import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
