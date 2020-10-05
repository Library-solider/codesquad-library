package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.bookopenapi.BookData;
import kr.codesquad.library.admin.domain.bookopenapi.BookFormRequest;
import kr.codesquad.library.domain.book.Book;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
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

    @CsvSource({"9791186697276, 제대로 배우는 도커, 아드리안 모트(Adrian Mouat), 비제이퍼블릭, " +
               "http://bimage.interpark.com/goods_image/0/7/3/1/263240731s.jpg, 2016-12-26, 12345"})
    @ParameterizedTest
    public void Open_API로부터_도서_데이터를_가져온다(String isbn, String title, String author, String publisher,
                                                String imageUrl, LocalDate publicationDate, String wrongIsbn) {
        //when
        BookData bookData = bookAdminService.findBookDataFromOpenApi(isbn);
        BookData emptyData = bookAdminService.findBookDataFromOpenApi(wrongIsbn);

        //then
        assertAll(
                () -> assertThat(bookData.getIsbn()).isEqualTo(isbn),
                () -> assertThat(bookData.getTitle()).isEqualTo(title),
                () -> assertThat(bookData.getAuthor()).isEqualTo(author),
                () -> assertThat(bookData.getPublisher()).isEqualTo(publisher),
                () -> assertThat(bookData.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(bookData.getPublicationDate()).isEqualTo(publicationDate)
        );

        assertThat(emptyData.getIsbn()).isNull();
    }

    @CsvSource({"'TCP/IP 쉽게, 더 쉽게', Description, 리브로웍스, 제이펍, 9791185890678, " +
                "http://bimage.interpark.com/goods_image/2/9/3/5/257842935s.jpg, 5, 1, 2016-09-21"})
    @ParameterizedTest
    public void 새로운_도서_데이터를_등록한다(String title, String description, String author, String publisher, String isbn,
                                        String imageUrl, Long categoryId, Long bookcaseId,
                                        LocalDate publicationDate) {
        //given
        BookFormRequest bookFormRequest = new BookFormRequest(title, description, author,
                                                                             publisher, isbn, imageUrl, categoryId,
                                                                             bookcaseId, publicationDate);

        //when
        Long newBookId = bookAdminService.createNewBook(bookFormRequest);
        Book book = bookAdminRepository.findById(newBookId).orElseGet(Book::new);

        //then
        assertAll(
                () -> assertThat(book.getDescription()).isEqualTo(description),
                () -> assertThat(book.getAuthor()).isEqualTo(author),
                () -> assertThat(book.getPublisher()).isEqualTo(publisher),
                () -> assertThat(book.getIsbn()).isEqualTo(isbn),
                () -> assertThat(book.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(book.getCategory().getId()).isEqualTo(categoryId),
                () -> assertThat(book.getBookcase().getId()).isEqualTo(bookcaseId),
                () -> assertThat(book.getPublicationDate()).isEqualTo(publicationDate)
        );
    }
}