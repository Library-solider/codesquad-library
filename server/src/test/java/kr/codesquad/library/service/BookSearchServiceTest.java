package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.response.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookSearchServiceTest {

    @Autowired
    private BookSearchService bookSearchService;

    @Test
    public void 각_카테고리_6개의_도서를_가져온다() {
        List<BookResponse> books = bookSearchService.findTop6BooksByCategory(1L);

        assertThat(books.size()).isEqualTo(6);
    }

    @CsvSource({"1, 1"})
    @ParameterizedTest
    public void 각_카테고리별_도서를_페이지씩_가져온다(Long categoryId, int page) {
        List<BookResponse> books = bookSearchService.findByCategoryIdBooks(categoryId, page);
        assertThat(books.size()).isEqualTo(20);
    }

}
