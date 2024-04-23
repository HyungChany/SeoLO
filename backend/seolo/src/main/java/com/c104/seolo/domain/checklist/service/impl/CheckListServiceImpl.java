package com.c104.seolo.domain.checklist.service.impl;

import com.c104.seolo.domain.checklist.dto.CheckListTemplateDto;
import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.repository.CheckListRepository;
import com.c104.seolo.domain.checklist.repository.CheckListTemplateRepository;
import com.c104.seolo.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;
    private final CheckListTemplateRepository checkListTemplateRepository;

    @Override
    public GetCheckListResponse getCheckListByCompany(Long company_id) {
        return null;
    }
}
