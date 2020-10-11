package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDataResponse;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.admin.domain.bookcase.BookcaseSummary;
import kr.codesquad.library.admin.domain.category.CategorySummary;
import kr.codesquad.library.domain.book.Book;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class BookcaseAdminServiceTest {

    @Autowired
    BookcaseAdminService bookcaseAdminService;

    @Autowired
    BookcaseAdminRepository bookcaseAdminRepository;

    @CsvSource({"15, 1, 430"})
    @ParameterizedTest
    public void 모든_도서위치를_조회한다(int bookcaseCount, Long bookcaseId, int bookCount) {
        //when
        List<BookcaseDetail> bookcases = bookcaseAdminService.findAllBookcases();
        BookcaseDetail bookcaseDetail = bookcases.stream()
                                                 .filter(bookcase -> bookcase.getId().equals(bookcaseId))
                                                 .findAny()
                                                 .orElse(null);

        //then
        assertThat(bookcases.size()).isEqualTo(bookcaseCount);
        assertThat(bookcaseDetail.getBookCount()).isEqualTo(bookCount);
    }

    @ParameterizedTest
    @MethodSource("provideSpecificBookcaseDataSource")
    public void 특정_도서위치에_해당하는_도서목록을_조회한다(Long bookcaseId, int page, int bookCount,
                                                   int categoryCount, int bookcaseCount) {
        //when
        BookcaseDataResponse bookcaseData = bookcaseAdminService.findBookcaseDataById(bookcaseId, page);
        BookcaseSummary bookcase = bookcaseData.getBookcase();
        List<BookSummary> books = bookcaseData.getBooks();
        List<CategorySummary> categories = bookcaseData.getCategories();
        List<BookcaseSummary> bookcases = bookcaseData.getBookcases();

        //then
        assertAll(
                () -> assertThat(bookcase.getId()).isEqualTo(bookcaseId),
                () -> assertThat(books.size()).isEqualTo(bookCount),
                () -> assertThat(categories.size()).isEqualTo(categoryCount),
                () -> assertThat(bookcases.size()).isEqualTo(bookcaseCount)
        );
    }

    static Stream<Arguments> provideSpecificBookcaseDataSource() {
        return Stream.of(
                Arguments.of(3L, 1, 10, 5, 15)
        );
    }
}
