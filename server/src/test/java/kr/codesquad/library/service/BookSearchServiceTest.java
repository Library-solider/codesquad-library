package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class BookSearchServiceTest {

    @Autowired
    private BookSearchService bookSearchService;

    @Test
    public void 각_카테고리_6개의_도서를_가져온다() {

        //when
        List<BookResponse> books = bookSearchService.findTop6BooksByCategory(1L);

        //then
        assertThat(books.size()).isEqualTo(6);
    }

    @CsvSource({"1, 1"})
    @ParameterizedTest
    public void 각_카테고리별_도서를_페이지씩_가져온다(Long categoryId, int page) {

        //when
        List<BookResponse> books = bookSearchService.findByCategoryIdBooks(categoryId, page);

        //then
        assertThat(books.size()).isEqualTo(20);
    }

    @CsvSource({"1"})
    @ParameterizedTest
    public void 빌려가지_않은_도서상세정보_가져오기(Long id) throws Exception {

        //when
        BookDetailResponse bookDetailResponse = bookSearchService.findByBookId(id);

        //then
        assertAll(
                () -> assertThat(bookDetailResponse).isNotNull(),
                () -> assertThat(bookDetailResponse.isBooksInStock()).isTrue(),
                () -> assertThat(bookDetailResponse.getBookBorrower()).isNull()
        );
    }
}
