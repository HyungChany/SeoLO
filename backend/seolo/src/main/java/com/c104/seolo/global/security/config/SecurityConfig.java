package com.c104.seolo.global.security.config;

import com.c104.seolo.domain.user.enums.ROLES;
import com.c104.seolo.global.security.filter.DaoCompanyCodeAuthenticationFilter;
import com.c104.seolo.global.security.handler.SeoloFailureHandler;
import com.c104.seolo.global.security.handler.SeoloSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final SeoloSuccessHandler seoloSuccessHandler;
    private final SeoloFailureHandler seoloFailureHandler;

    @Autowired
    public SecurityConfig(SeoloSuccessHandler seoloSuccessHandler, SeoloFailureHandler seoloFailureHandler) {
        this.seoloSuccessHandler = seoloSuccessHandler;
        this.seoloFailureHandler = seoloFailureHandler;
    }


    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        // MANAGER는 WORKER의 인가 권한을 모두 가지고 있다.
        // WORKER에게도 인가를 열어주고 싶은 메서드는 @Secured("hasRole('WORKER')") 를 붙인다.
        roleHierarchy.setHierarchy(AuthorityConfig.getHierarchy());
        return roleHierarchy;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
            .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfig()))
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(new DaoCompanyCodeAuthenticationFilter(authenticationManager, seoloFailureHandler), UsernamePasswordAuthenticationFilter.class);

        http.formLogin(f ->
                f.loginPage("/test/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
        );

        // 영구 로그인 인증
        http.rememberMe(re ->
                re.alwaysRemember(true)
                .key("persistenceKey")
                .authenticationSuccessHandler(seoloSuccessHandler)
        );

        // 동시 세션 제어
        http
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) // 기존 사용자 종료
                .expiredUrl("/test/login")
        );

        http
                .authorizeHttpRequests(au -> au
                .requestMatchers("/error","/join", "/login", "/test/login").permitAll()
                .anyRequest().hasRole(ROLES.MANAGER.name())
        );

        return http.build();
    }


    public CorsConfigurationSource corsConfig() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOrigins(Collections.singletonList("*")); // 모든 Origin 허용
            config.setAllowCredentials(false); // 모든 도메인을 허용할 때는 false로 설정해야 함
            return config;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
