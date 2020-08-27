package kr.codesquad.library.global.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("JWT 파싱테스트")
    @Test
    public void parseJwt() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwibmFtZSI6IlN1bm55OTIiLCJhdmF0YXJVcmwiOiJodHRwczovL2F2YXRhcnMxLmdpdGh1YnVzZXJjb250ZW50LmNvbS91LzQ5MTQ0NjYyP3Y9NCIsImlhdCI6MTU5ODQyODQyNywiZXhwIjoxNTk4NDI5MjkxfQ.V6sABtGjad9LutKbQHyRtJqTfi9glYR7CQQa3YfkS0M";
        Long id = jwtProvider.getAccountIdFromToken(jwt);
        System.out.println(id);

        assertThat(id).isInstanceOf(Long.class);
    }
}
