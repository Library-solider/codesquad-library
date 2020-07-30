package kr.codesquad.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BookSearchService.class})
class BookSearchServiceTest {

    @DisplayName("카테고리 상위 6개를 가져올 수 있다.")
    @Test
    void 각_카테고리_6개의_도서를_가져온다() {

    }

}
