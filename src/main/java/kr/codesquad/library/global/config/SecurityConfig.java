package kr.codesquad.library.global.config;

import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.config.oauth.service.CustomOAuth2UserService;
import kr.codesquad.library.global.config.oauth.service.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        http.authorizeRequests()
                .antMatchers("/", "/v1/main", "/v1/category/**", "/v1/search/**", "/v1/oauth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/books/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/books/**").hasAnyRole(LibraryRole.USER.name(), LibraryRole.ADMIN.name())
                .anyRequest().authenticated();

        http.oauth2Login()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
            .and()
                .successHandler(oAuth2SuccessHandler);

        //todo: 필터를 추가하여 JWT확인하는 코드가 필요하다.
    }
}
