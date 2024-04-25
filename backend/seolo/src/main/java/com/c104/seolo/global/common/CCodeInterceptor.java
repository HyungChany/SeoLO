package com.c104.seolo.global.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class CCodeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String companyCode = request.getHeader("Company-Code");
        if (companyCode == null || companyCode.isEmpty()) {
//            throw new CommonException("코드 보내세요")
//            NO_COMPANY_CODE("회사 코드를 보내지 않았습니다.", HttpStatus.BAD_REQUEST),;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // 인터셉트 중지
        }
        // Company-Code 헤더가 있는 경우 처리할 로직
        // Company Table의 값과 equals(완전일치)하는 값이 있는지 검증
        // 완전일치하는 코드 없으면 CommonException
//        NOT_EXIST_COMPANY_CODE("해당 회사가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
//        return false

//        일치하는 값이 있으면
        return true; // 다음 인터셉터 또는 핸들러 실행
    }

//    @Override
//    public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView mav) throws Exception{
//        return super.postHandle (request,response,handler,mav);
//    }
//
//    @Override
//    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
//        return super.afterCompletion (request,response,handler,ex);
//    }
}
