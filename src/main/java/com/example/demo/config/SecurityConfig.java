package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 스프링 설정 클래스 지정, 등록된 Bean 생성 시점
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig { // 스프링에서 보안 관리 클래스

    @Bean // 명시적 의존성 주입 : Autowired와 다름
    // 5.7버전 이저 WebSecurityConfigurerAdapter 사용
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .addHeaderWriter((request, response) -> {
                            // X-XSS-Protection 헤더 설정: XSS 공격 방어 활성화 및 브라우저가 페이지 렌더링을 차단하도록 설정
                            response.setHeader("X-XSS-Protection", "1; mode=block");
                        }))
                // .csrf(withDefaults()) // CSRF 보호 활성화 (기본 설정 사용)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        // 세션 만료 시 이동할 페이지 URL 설정
                        .invalidSessionUrl("/session-expired")
                        // 사용자당 최대 세션 수를 1개로 제한
                        .maximumSessions(1)
                        // 최대 세션 수 초과 시, 새 로그인 시도를 차단 (true)하고 기존 세션을 유지
                        .maxSessionsPreventsLogin(true));
        return http.build(); // 필터 체인을 통해 보안설정(HttpSecurity)을 반환
    }

    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 저장
    }
}