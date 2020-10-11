package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDataResponse;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.admin.domain.bookcase.BookcaseSummary;
import kr.codesquad.library.admin.domain.category.CategorySummary;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.global.error.exception.admin.DeleteEntityDeniedException;
import kr.codesquad.library.global.error.exception.domain.BookcaseNotFoundException;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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


    @ParameterizedTest
    @MethodSource("changeBookcaseLocationSource")
    public void 도서위치의_이름을_변경한다(Long bookcaseId, String newLocation) {
        //when
        bookcaseAdminService.updateBookcaseLocation(bookcaseId, newLocation);
        Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);

        //then
        assertThat(bookcase.getLocation()).isEqualTo(newLocation);
    }

    @CsvSource({"새 도서위치, 16"})
    @ParameterizedTest
    public void 새로운_도서위치를_등록한다(String location, int bookcaseCount) {
        //when
        Long bookcaseId = bookcaseAdminService.createNewBookcase(location);
        Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();

        //then
        assertThat(bookcase.getLocation()).isEqualTo(location);
        assertThat(bookcases.size()).isEqualTo(bookcaseCount);
    }

    @CsvSource({"5, 1"})
    @ParameterizedTest
    public void 도서위치를_삭제한다(Long bookcaseId, Long bookExistBookcaseId) {
        //when
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        int oldCount = bookcases.size();

        bookcaseAdminService.deleteBookcase(bookcaseId);

       List<Bookcase> newBookcases = bookcaseAdminRepository.findAll();
       int newCount = newBookcases.size();

        //then
        assertThat(oldCount).isEqualTo(newCount + 1);
        assertThrows(DeleteEntityDeniedException.class,
                () ->bookcaseAdminService.deleteBookcase(bookExistBookcaseId));
        assertThrows(BookcaseNotFoundException.class,
                () -> bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new));
    }

    static Stream<Arguments> provideSpecificBookcaseDataSource() {
        return Stream.of(
                Arguments.of(3L, 1, 10, 5, 15)
        );
    }

    static Stream<Arguments> changeBookcaseLocationSource() {
        return Stream.of(
                Arguments.of(1L, "새로운 도서 위치")
        );

    }
}
