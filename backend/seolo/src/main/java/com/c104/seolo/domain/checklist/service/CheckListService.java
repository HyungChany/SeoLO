package com.c104.seolo.domain.checklist.service;

import com.c104.seolo.domain.checklist.dto.request.CheckListRequest;
import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import org.springframework.stereotype.Service;

@Service
public interface CheckListService {
    GetCheckListResponse getCheckListByCompany(String company_code);
    void createCheckList(CheckListRequest checkListRequest, String company_code);
    void deleteCheckListByCompany(String company_code, Long checklist_id);
}
