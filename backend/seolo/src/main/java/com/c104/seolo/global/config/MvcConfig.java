
package com.c104.seolo.global.config;

import com.c104.seolo.global.common.CCodeInterceptor;
import com.c104.seolo.headquarter.company.repository.CompanyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final CompanyRepository companyRepository;

    public MvcConfig(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cCodeInterceptor())
                .addPathPatterns("/**") // 모든 URL에 인터셉터를 적용
                .excludePathPatterns("/login","/error"); // 예외 처리할 URL 패턴 지정
    }

    @Bean
    public CCodeInterceptor cCodeInterceptor() {
        return new CCodeInterceptor(companyRepository);
    }
}
