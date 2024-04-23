package com.c104.seolo.domain.checklist.service;

import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import org.springframework.stereotype.Service;

@Service
public interface CheckListService {
    GetCheckListResponse getCheckListByCompany(Long company_id);
}
