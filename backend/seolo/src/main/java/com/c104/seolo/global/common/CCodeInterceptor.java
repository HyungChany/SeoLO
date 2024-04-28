package com.c104.seolo.global.common;

import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.headquarter.company.exception.CompanyErrorCode;
import com.c104.seolo.headquarter.company.repository.CompanyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class CCodeInterceptor implements HandlerInterceptor {
    private final CompanyRepository companyRepository;
    // 생성자를 통한 의존성 주입
    public CCodeInterceptor(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String companyCode = request.getHeader("Company-Code");
        if (companyCode == null || companyCode.isEmpty()) {
            // 헤더에 회사코드를 안보냈거나 NULL을 보낸 경우
            throw new CommonException(CompanyErrorCode.NO_COMPANY_CODE);
        } else if (companyRepository.findByCompanyCodeEquals(companyCode) == null) {
            // 회사코드를 보냈지만, 테이블에서 찾을 수 없는 경우
            throw new CommonException(CompanyErrorCode.NOT_EXIST_COMPANY_CODE);
        }
        // 일치하는 값이 있으면 controller 실행하도록
        return true; // 다음 인터셉터 또는 핸들러 실행
    }

}
