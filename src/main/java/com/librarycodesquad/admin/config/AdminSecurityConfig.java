package com.librarycodesquad.admin.config;

import com.librarycodesquad.admin.config.security.AdminAccessDeniedHandler;
import com.librarycodesquad.admin.config.security.AdminAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Order(2)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminAuthenticationEntryPoint authenticationEntryPoint;
    private final AdminAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.cors()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
        http.exceptionHandling()
                .accessDeniedPage("/templates/access-failure");

        http.antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin", "/admin/login/failure").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/admin")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/users/guest")
                .failureUrl("/admin/login/failure")
                .usernameParameter("adminName")
                .passwordParameter("password");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
