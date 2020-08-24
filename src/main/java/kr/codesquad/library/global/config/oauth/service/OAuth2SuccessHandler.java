package kr.codesquad.library.global.config.oauth.service;

import kr.codesquad.library.global.utils.CookieUtils;
import kr.codesquad.library.global.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String token = jwtProvider.generateToken(authentication);
        CookieUtils.addCookie(response, "jwt", token, jwtProvider.getTokenExpiration());
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080");
    }
}
