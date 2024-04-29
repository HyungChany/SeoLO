package com.c104.seolo.domain.checklist.service.impl;

import com.c104.seolo.domain.checklist.dto.CheckListDto;
import com.c104.seolo.domain.checklist.dto.CheckListTemplateDto;
import com.c104.seolo.domain.checklist.dto.info.CheckListInfo;
import com.c104.seolo.domain.checklist.dto.info.CheckListTemplateInfo;
import com.c104.seolo.domain.checklist.dto.request.CheckListRequest;
import com.c104.seolo.domain.checklist.dto.response.GetCheckListResponse;
import com.c104.seolo.domain.checklist.entity.CheckList;
import com.c104.seolo.domain.checklist.exception.CheckListErrorCode;
import com.c104.seolo.domain.checklist.repository.CheckListRepository;
import com.c104.seolo.domain.checklist.repository.CheckListTemplateRepository;
import com.c104.seolo.domain.checklist.service.CheckListService;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.headquarter.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CompanyRepository companyRepository;

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
        Optional<List<CheckListInfo>> checklists = checkListRepository.findByCompanyEquals(company_code);

        List<CheckListTemplateDto> checkListTemplateDtos = getCheckListTemplates(basicList);
        List<CheckListDto> checkListDtos = getCheckLists(checklists);

        return GetCheckListResponse.builder()
                .basic_checklists(checkListTemplateDtos)
                .checklists(checkListDtos)
                .build();
    }

    @Override
    public void createCheckList(CheckListRequest checkListRequest, String company_code){
        boolean listAlreadyExists = checkListRepository.existsByCheckListContextIgnoreCase(checkListRequest.getContext(), company_code);
        if (listAlreadyExists) {
            throw new CommonException(CheckListErrorCode.CHECK_LIST_ALREADY_EXISTS);
        }

        CheckList checkList = CheckList.builder()
                .checkListContext(checkListRequest.getContext())
                .company(companyRepository.findByCompanyCodeEquals(company_code))
                .build();
        checkListRepository.save(checkList);
    }

    @Override
    public void updateCheckList(CheckListRequest checkListRequest, Long checklist_id, String company_code){
        CheckList checkList = checkListRepository.findById(checklist_id)
                .orElseThrow(() -> new CommonException(CheckListErrorCode.NOT_EXIST_CHECK_LIST));

        if (checkList.getContext().equals(checkListRequest.getContext())) {
            throw new CommonException(CheckListErrorCode.CHECK_LIST_ALREADY_EXISTS);
        }

        checkList.setContext(checkListRequest.getContext());
        checkListRepository.save(checkList);
    }

    @Override
    public void deleteCheckListByCompany(String company_code, Long check_list_id) {
        CheckList checkList = checkListRepository.findById(check_list_id)
                .orElseThrow(() -> new CommonException(CheckListErrorCode.NOT_EXIST_CHECK_LIST));

        if (!checkList.getCompany().getCompanyCode().equals(company_code)) {
            throw new CommonException(CheckListErrorCode.NOT_COMPANY_CHECK_LIST);
        }
        checkListRepository.delete(checkList);
    }
}
