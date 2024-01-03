package org.choongang.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 E - 로그인, 로그아웃 */

        /* 인가설정 s - 접근 통제 */
        // hasAuthority(..) hasAnyAuthority(...),harRole,hasAuyRole
        // ROLE_ 롤 명칭
        // hasAuthority('ADMIN')
        // ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        // hasRole('ADMIN')
        http.authorizeHttpRequests(c-> {
            c.requestMatchers("/mypage/**").authenticated() // 회원 전용
                    .requestMatchers("/admin/**").hasAnyAuthority
                            ("ADMIN","MANAGER")// 매니저와 회원만 접근 가능
                    .anyRequest().permitAll(); // 그외 모든 페이지 입장 가능
        });

        http.exceptionHandling(c->{
            c.authenticationEntryPoint((req, res, e) -> {
                String URL = req.getRequestURI();
                if (URL.indexOf("/admin") != -1) {// 관리자 페이지
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }else { // 회원전용 페이지
                    res.sendRedirect(req.getContextPath() + "/member/login");
                }

            });
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}