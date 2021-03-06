package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.book.BookAdminRepository;
import com.librarycodesquad.admin.domain.book.BookData;
import com.librarycodesquad.admin.domain.book.BookSummary;
import com.librarycodesquad.admin.domain.book.request.BookFormRequest;
import com.librarycodesquad.admin.domain.book.request.BookMoveRequest;
import com.librarycodesquad.admin.domain.book.response.BookSummaryResponse;
import com.librarycodesquad.admin.domain.bookcase.BookcaseAdminRepository;
import com.librarycodesquad.admin.domain.category.CategoryAdminRepository;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.prod.global.error.exception.domain.BookNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class BookAdminServiceTest {

    @Autowired
    BookAdminService bookAdminService;

    @Autowired
    BookAdminRepository bookAdminRepository;

    @Autowired
    CategoryAdminRepository categoryAdminRepository;

    @Autowired
    BookcaseAdminRepository bookcaseAdminRepository;

    @CsvSource({"3, 10, 44, 10, 5, 1, 1, 10, 1, 11"})
    @ParameterizedTest
    public void 모든_도서를_조회한다(int page, int pageSize, int totalPages, int pageGroupSize,
                                int totalPageGroups, int currentPageGroup, int startPageOfPageGroup,
                                int endPageOfPageGroup, int endPageOfPreviousPageGroup, int startPageOfNextPageGroup) {
        //when
        BookSummaryResponse books = bookAdminService.findAllBooks(page);
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
        Book book = bookAdminRepository.findById(newBookId).orElseThrow(BookNotFoundException::new);

        //then
        assertAll(
                () -> assertThat(book.getDescription()).isEqualTo(description),
                () -> assertThat(book.getAuthor()).isEqualTo(author),
                () -> assertThat(book.getPublisher()).isEqualTo(publisher),
                () -> assertThat(book.getIsbn()).isEqualTo(isbn),
                () -> assertThat(book.getImageUrl()).isEqualTo(imageUrl),
                () -> Assertions.assertThat(book.getCategory().getId()).isEqualTo(categoryId),
                () -> Assertions.assertThat(book.getBookcase().getId()).isEqualTo(bookcaseId),
                () -> assertThat(book.getPublicationDate()).isEqualTo(publicationDate)
        );
    }

    @CsvSource({"3, Changed Title, Changed description, Changed author, Changed publisher, Changed isbn, " +
                "Changed imageUrl, 1, 3, 1992-04-25"})
    @ParameterizedTest
    public void 도서정보를_업데이트_한다(Long bookId, String title, String description, String author, String publisher,
                                    String isbn, String imageUrl, Long categoryId, Long bookcaseId,
                                    LocalDate publicationDate) {
        //given
        BookFormRequest bookFormRequest = new BookFormRequest(title, description, author,
                                                              publisher, isbn, imageUrl, categoryId,
                                                              bookcaseId, publicationDate);

        //when
        Long updatedBookId = bookAdminService.updateBook(bookId, bookFormRequest);
        Book book = bookAdminRepository.findById(updatedBookId).orElseThrow(BookNotFoundException::new);

        //then
        assertAll(
                () -> assertThat(book.getId()).isEqualTo(updatedBookId),
                () -> assertThat(book.getTitle()).isEqualTo(title),
                () -> assertThat(book.getDescription()).isEqualTo(description),
                () -> assertThat(book.getAuthor()).isEqualTo(author),
                () -> assertThat(book.getPublisher()).isEqualTo(publisher),
                () -> assertThat(book.getIsbn()).isEqualTo(isbn),
                () -> assertThat(book.getImageUrl()).isEqualTo(imageUrl),
                () -> Assertions.assertThat(book.getCategory().getId()).isEqualTo(categoryId),
                () -> Assertions.assertThat(book.getBookcase().getId()).isEqualTo(bookcaseId),
                () -> assertThat(book.getPublicationDate()).isEqualTo(publicationDate)
        );
    }

    @MethodSource("provideBookMoveRequestSource")
    @ParameterizedTest
    public void 도서를_다른_그룹으로_이동한다(List<Long> bookIds, Long categoryId,
                                        Long bookcaseId, int bookCountAfterChange) {
        //when
        BookMoveRequest bookMoveRequest = BookMoveRequest.of(bookIds, categoryId, bookcaseId);
        bookAdminService.changeGroup(bookMoveRequest);
        List<Book> books = bookAdminRepository.findAllByIdIn(bookIds);
        List<Book> newCategoryBooks = books.stream()
                                           .filter(book -> {
                                                Category category = book.getCategory();
                                                Long changedCategoryId = category.getId();
                                                return changedCategoryId.equals(categoryId);
                                            })
                                           .collect(Collectors.toList());
        List<Book> newBookcaseBooks = books.stream()
                                           .filter(book -> {
                                               Bookcase bookcase = book.getBookcase();
                                               Long changedBookcaseId = bookcase.getId();
                                               return changedBookcaseId.equals(bookcaseId);
                                           })
                                           .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(newCategoryBooks.size()).isEqualTo(bookCountAfterChange),
                () -> assertThat(newBookcaseBooks.size()).isEqualTo(bookCountAfterChange)
        );
    }

    @CsvSource({"458"})
    @ParameterizedTest
    public void 특정_도서를_삭제한다(Long bookId) {
        //when
        List<Book> books = bookAdminRepository.findAll();
        int oldCount = books.size();

        bookAdminService.deleteBook(bookId);

        List<Book> newBooks = bookAdminRepository.findAll();
        int newCount = newBooks.size();

        //then
        assertThrows(BookNotFoundException.class,
                () -> bookAdminRepository.findById(bookId).orElseThrow(BookNotFoundException::new));
        assertThat(oldCount).isEqualTo(newCount + 1);
    }

    static Stream<Arguments> provideBookMoveRequestSource() {
        return Stream.of(
                Arguments.of(Arrays.asList(458L, 457L, 456L), 3L, 3L, 3)
        );
    }
}
