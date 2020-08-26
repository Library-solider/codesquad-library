package kr.codesquad.library.global.config.oauth.security;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.global.config.oauth.dto.SecurityUser;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
import kr.codesquad.library.global.error.exception.domain.JwtNotFoundException;
import kr.codesquad.library.global.error.exception.domain.UnauthorizedJwtException;
import kr.codesquad.library.global.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);
        UserDetails userDetails = (UserDetails) SecurityUser.renew(getAccount(jwt));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new JwtNotFoundException();
        } else if (!token.startsWith("Bearer ")){
            throw new UnauthorizedJwtException();
        }
        return token.substring(7);
    }

    private Account getAccount(String jwt) {
        Long accountId = jwtProvider.getAccountIdFromToken(jwt);
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }
}
