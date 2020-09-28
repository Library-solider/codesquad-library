package kr.codesquad.library.global.config;

import kr.codesquad.library.global.config.oauth.security.CustomAccessDeniedHandler;
import kr.codesquad.library.global.config.oauth.security.CustomAuthenticationEntryPoint;
import kr.codesquad.library.global.config.oauth.security.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
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

        http.authorizeRequests()
                .antMatchers("/", "/v1/main", "/v1/category/**", "/v1/search/**", "/oauth2/redirect").permitAll()
//                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/books/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/books/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers( "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.oauth2Login()
                .defaultSuccessUrl(redirectUrl, true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
