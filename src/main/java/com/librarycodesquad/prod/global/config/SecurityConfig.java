package com.librarycodesquad.prod.global.config;

import com.librarycodesquad.prod.global.config.oauth.security.CustomAccessDeniedHandler;
import com.librarycodesquad.prod.global.config.oauth.security.CustomAuthenticationEntryPoint;
import com.librarycodesquad.prod.global.config.oauth.security.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Value("${app.redirectUrl}")
    private String redirectUrl;

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
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/v1/main", "/v1/category/**", "/v1/search/**").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/books/**").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/books/**").hasRole("USER")
            .antMatchers(HttpMethod.PUT, "/v1/books/**").hasRole("USER")
            .anyRequest().authenticated();

        http.oauth2Login()
                .authorizationEndpoint()
                .and()
                .defaultSuccessUrl(redirectUrl, true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
