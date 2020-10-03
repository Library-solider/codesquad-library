package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.common.PagingProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class BookAdminServiceTest {

    @Autowired
    BookAdminService bookAdminService;

    @Autowired
    BookAdminRepository bookAdminRepository;

    @CsvSource({"3, 10, 44, 10, 5, 1, 1, 10, 1, 11"})
    @ParameterizedTest
    public void 모든_도서를_조회한다(int page, int pageSize, int totalPages, int pageGroupSize,
                                int totalPageGroups, int currentPageGroup, int startPageOfPageGroup,
                                int endPageOfPageGroup, int endPageOfPreviousPageGroup, int startPageOfNextPageGroup) {
        //when
        BooksWithPagingResponse books = bookAdminService.findAllBooks(page);
        List<BookSummary> bookSummaries = books.getBookSummaries();
        PagingProperties pagingProperties = books.getPagingProperties();

        //then
        assertThat(bookSummaries.size()).isEqualTo(pageSize);

        assertAll(
                () -> assertThat(pagingProperties.getPageSize()).isEqualTo(pageSize),
                () -> assertThat(pagingProperties.getTotalPages()).isEqualTo(totalPages),
                () -> assertThat(pagingProperties.getPageGroupSize()).isEqualTo(pageGroupSize),
                () -> assertThat(pagingProperties.getTotalPageGroups()).isEqualTo(totalPageGroups),
                () -> assertThat(pagingProperties.getCurrentPage()).isEqualTo(page),
                () -> assertThat(pagingProperties.getCurrentPageGroup()).isEqualTo(currentPageGroup),
                () -> assertThat(pagingProperties.getStartPageOfPageGroup()).isEqualTo(startPageOfPageGroup),
                () -> assertThat(pagingProperties.getEndPageOfPageGroup()).isEqualTo(endPageOfPageGroup),
                () -> assertThat(pagingProperties.getEndPageOfPreviousPageGroup()).isEqualTo(endPageOfPreviousPageGroup),
                () -> assertThat(pagingProperties.getStartPageOfNextPageGroup()).isEqualTo(startPageOfNextPageGroup)
        );

    }
}