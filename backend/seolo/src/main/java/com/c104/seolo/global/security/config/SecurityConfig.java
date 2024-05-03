package com.c104.seolo.global.security.config;

import com.c104.seolo.global.security.filter.DaoCompanyCodeAuthenticationFilter;
import com.c104.seolo.global.security.handler.SeoloLoginFailureHandler;
import com.c104.seolo.global.security.handler.SeoloLoginSuccessHandler;
import com.c104.seolo.global.security.handler.SeoloLogoutSuccessHandler;
import com.c104.seolo.global.security.jwt.filter.JwtValidationFilter;
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
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // MANAGER는 WORKER의 인가 권한을 모두 가지고 있다.
        // MANAGER에게만 인가를 열어주고 싶은 메서드는 @Secured("ROLE_MANAGER") 를 붙인다.
        roleHierarchy.setHierarchy(AuthorityConfig.getHierarchy());
        return roleHierarchy;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // JWT 토큰 전용
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtValidationFilter jwtValidationFilter) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

        // 인증,인가 커버리지 설정
        http
                .authorizeHttpRequests(au -> au
                        .requestMatchers("/error","/join", "/login", "/test/login","/test","/test/**").permitAll()
                        .anyRequest().hasRole("WORKER")
                );

        return http.build();
    }
    
    
    

//    세션인증전용
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http,
//                                    AuthenticationManager authenticationManager,
//                                    SeoloLoginSuccessHandler seoloLoginSuccessHandler,
//                                    SeoloLoginFailureHandler seoloLoginFailureHandler,
//                                    SeoloLogoutSuccessHandler seoloLogoutSuccessHandler) throws Exception {
//        http
//            .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfig()))
//            .csrf(AbstractHttpConfigurer::disable)
//            .addFilterBefore(new DaoCompanyCodeAuthenticationFilter(authenticationManager, seoloLoginSuccessHandler, seoloLoginFailureHandler), UsernamePasswordAuthenticationFilter.class);
//
//        // 영구 로그인 인증
////        http.rememberMe(re ->
////                re.alwaysRemember(true)
////                .key("persistenceKey")
////                .authenticationSuccessHandler(seoloLoginSuccessHandler)
////        );
//
//        // 로그아웃 설정 추가
//        http.logout(logout -> logout
//                .logoutUrl("/logout") // 로그아웃 처리 URL 지정
//                .logoutSuccessHandler(seoloLogoutSuccessHandler)
//                .invalidateHttpSession(true) // 세션 무효화
//                .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
//                .clearAuthentication(true) // 인증 정보 클리어
//        );
//        
//        // 동시 세션 제어
//        http
//                .sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false) // 기존 사용자 종료
//                .expiredUrl("/login")
//        );
//
//        http
//                .securityContext((securityContext) -> {
//                    securityContext.securityContextRepository(delegatingSecurityContextRepository());
//                    securityContext.requireExplicitSave(true);
//        });
//        
//        // 인증,인가 커버리지 설정
//        http
//                .authorizeHttpRequests(au -> au
//                .requestMatchers("/error","/join", "/login", "/test/login","/test","/test/**").permitAll()
//                .anyRequest().hasRole("WORKER")
//        );
//
//        return http.build();
//    }

    @Bean
    public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
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
