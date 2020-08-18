package kr.codesquad.library.global.config.oauth;

import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.config.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/v1/main", "/v1/category/**", "/v1/search/**", "/v1/oauth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/books/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/books/**")
                                            .hasAnyRole(LibraryRole.USER.name(), LibraryRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/");

        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.csrf().disable()
                .headers().frameOptions().disable();
    }
}
