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
import org.springframework.stereotype.Service;

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

    private List<CheckListDto> getCheckLists(List<CheckListInfo> checkListInfoList) {
        return checkListInfoList.stream()
                .map(info -> CheckListDto.builder()
                        .id(info.getId())
                        .context(info.getContext())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public GetCheckListResponse getCheckListByCompany(String company_code) {
        List<CheckListTemplateInfo> basicList = checkListTemplateRepository.getCheckListTemplates();
        Optional<List<CheckListInfo>> checklists = checkListRepository.findByCompany(company_code);

        List<CheckListTemplateDto> checkListTemplateDtos = getCheckListTemplates(basicList);
//        Optional<List<CheckListDto>> checkListDtos = getCheckLists(checklists);

        return GetCheckListResponse.builder()
                .basic_checklists(checkListTemplateDtos)
//                .checklists(checkListDtos)
                .build();
    }
}
