
package com.c104.seolo.global.config;

import com.c104.seolo.global.common.CompanyCodeInterceptor;
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
        registry.addInterceptor(companyCodeInterceptor())
                .addPathPatterns("/**") // 모든 URL 에 인터셉터를 적용
                .excludePathPatterns("/login","/error","/join"); // 예외 처리할 URL 패턴 지정
    }

    @Bean
    public CompanyCodeInterceptor companyCodeInterceptor() {
        return new CompanyCodeInterceptor(companyRepository);
    }
}
