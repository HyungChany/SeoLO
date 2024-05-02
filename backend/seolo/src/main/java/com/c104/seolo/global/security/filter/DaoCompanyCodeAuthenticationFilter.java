package com.c104.seolo.global.security.filter;

import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;


@Slf4j
public class DaoCompanyCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String SPRING_SECURITY_FORM_COMPANY_CODE_KEY = "companyCode";
    private String companyCodeParameter = SPRING_SECURITY_FORM_COMPANY_CODE_KEY;
    private boolean postOnly = true;

    public DaoCompanyCodeAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        log.info("DaoCompanyfilter 진입 확인");
        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        String companyCode = obtainCompanyCode(request);

        DaoCompanycodeToken authRequest = new DaoCompanycodeToken(username, password, companyCode);
        // 요청 세부 정보 설정
        setDetails(request, authRequest);
        log.info("인증 전 DaoToken 생성 : {}",authRequest.toString());
        // 인증 매니저에 인증 객체 전달 및 인증 시도
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainCompanyCode(HttpServletRequest request) {
        return request.getParameter(this.companyCodeParameter);
    }

    public void setCompanyCodeParameter(String companyCodeParameter) {
        Assert.hasText(companyCodeParameter, "Company code parameter must not be empty or null");
        this.companyCodeParameter = companyCodeParameter;
    }

    public final String getCompanyCodeParameter() {
        return this.companyCodeParameter;
    }
}
