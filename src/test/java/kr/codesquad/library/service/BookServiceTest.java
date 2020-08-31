package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void 각_카테고리_6개의_도서를_가져온다() {

        //when
        List<BookResponse> books = bookService.findTop6BooksByCategory(1L);

        //then
        assertThat(books.size()).isEqualTo(6);
    }

    @CsvSource({"1, 1"})
    @ParameterizedTest
    public void 각_카테고리별_도서를_페이지씩_가져온다(Long categoryId, int page) {

        //when
        List<BookResponse> books = bookService.findByCategoryIdBooks(categoryId, page);

        //then
        assertThat(books.size()).isEqualTo(20);
    }

    @CsvSource({"1"})
    @ParameterizedTest
    public void 빌려가지_않은_도서상세정보_가져오기(Long id) {

        //when
        BookDetailResponse bookDetailResponse = bookService.findByBookId(id);

        //then
        assertAll(
                () -> assertThat(bookDetailResponse).isNotNull(),
                () -> assertThat(bookDetailResponse.isAvailable()).isTrue(),
                () -> assertThat(bookDetailResponse.getBookBorrower()).isNull()
        );
    }

    @CsvSource({"켄트 벡, 1, 4"})
    @ParameterizedTest
    public void 검색한_도서정보_가져오기(String searchWord, int result) {

        //when
        List<BookResponse> bookSearchResponse = bookService.searchBooksList(searchWord);

        //then
        assertAll(
                () -> assertThat(bookSearchResponse).isNotEmpty(),
                () -> assertThat(bookSearchResponse.size()).isEqualTo(result)
        );
    }
}
