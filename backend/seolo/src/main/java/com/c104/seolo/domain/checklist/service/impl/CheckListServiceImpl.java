package com.c104.seolo.domain.checklist.service.impl;

import com.c104.seolo.domain.checklist.dto.CheckListDto;
import com.c104.seolo.domain.checklist.dto.CheckListTemplateDto;
import com.c104.seolo.domain.checklist.dto.info.CheckListInfo;
import com.c104.seolo.domain.checklist.dto.info.CheckListTemplateInfo;
import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.repository.CheckListRepository;
import com.c104.seolo.domain.checklist.repository.CheckListTemplateRepository;
import com.c104.seolo.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;
    private final CheckListTemplateRepository checkListTemplateRepository;

    private List<CheckListTemplateDto> getCheckListTemplates(List<CheckListTemplateInfo> checkListTemplateInfoList) {
        return checkListTemplateInfoList.stream()
                .map(info  -> CheckListTemplateDto.builder()
                        .id(info.getId())
                        .context(info.getContext())
                        .build())
                .collect(Collectors.toList());
    }

    private List<CheckListDto> getCheckLists(Optional<List<CheckListInfo>> checkListInfoListOptional) {
        return checkListInfoListOptional.map(checkListInfoList ->
                checkListInfoList.stream()
                        .map(info -> CheckListDto.builder()
                                .id(info.getId())
                                .context(info.getContext())
                                .build())
                        .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }


    @Override
    public GetCheckListResponse getCheckListByCompany(String company_code) {
        List<CheckListTemplateInfo> basicList = checkListTemplateRepository.getCheckListTemplates();
        Optional<List<CheckListInfo>> checklists = checkListRepository.findByCompany(company_code);

        List<CheckListTemplateDto> checkListTemplateDtos = getCheckListTemplates(basicList);
        List<CheckListDto> checkListDtos = getCheckLists(checklists);

        return GetCheckListResponse.builder()
                .basic_checklists(checkListTemplateDtos)
                .checklists(checkListDtos)
                .build();
    }
}
