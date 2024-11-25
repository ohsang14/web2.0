package org.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 이 클래스가 설정 클래스임을 명시
@EnableWebSecurity // Spring Security를 활성화
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Spring Security 필터 체인을 정의하는 메서드
     *
     * @param http HttpSecurity 객체로, 보안 설정을 구성하는데 사용
     * @return SecurityFilterChain 객체
     * @throws Exception 설정 중 발생할 수 있는 예외
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 권한 설정
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // 모든 요청을 허용 ("/**" 매칭)
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // CSRF 설정
                .csrf((csrf) -> csrf
                        // H2-console 접근 시 CSRF 보호를 비활성화
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                // HTTP 헤더 설정
                .headers((headers) -> headers
                        // X-Frame-Options 헤더를 SAMEORIGIN으로 설정하여 iframe을 허용
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 로그인 설정
                .formLogin((formLogin) -> formLogin
                        // 로그인 페이지 경로 지정
                        .loginPage("/user/login")
                        // 로그인 성공 시 리디렉션할 기본 URL
                        .defaultSuccessUrl("/"))
                .logout((logout)->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))

        ;
        // 필터 체인 반환
        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder를 정의
     *
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘을 사용하는 암호화 방식
    }

    /**
     * AuthenticationManager를 Bean으로 등록
     *
     * @param authenticationConfiguration AuthenticationConfiguration 객체
     * @return AuthenticationManager 객체
     * @throws Exception 설정 중 발생할 수 있는 예외
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        // Spring Security가 제공하는 기본 인증 매니저 반환
        return authenticationConfiguration.getAuthenticationManager();
    }
}
