package kr.codesquad.library.global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration}")
    private int tokenExpiration;

    public String generateToken(Authentication authentication) {
        AccountPrincipal accountPrincipal = (AccountPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration);

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("name", accountPrincipal.getName());
        payloads.put("avatarUrl", accountPrincipal.getAvatarUrl());

        return Jwts.builder()
                .setSubject(Long.toString(accountPrincipal.getId()))
                .setClaims(payloads)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
