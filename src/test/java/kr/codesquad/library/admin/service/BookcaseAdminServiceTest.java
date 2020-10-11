package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
}
