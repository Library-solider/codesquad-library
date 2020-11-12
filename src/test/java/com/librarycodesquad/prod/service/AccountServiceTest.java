package com.librarycodesquad.prod.service;

import com.librarycodesquad.prod.domain.account.response.AccountProfileResponse;
import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.AccountRepository;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import com.librarycodesquad.prod.domain.account.response.AccountMyPageResponse;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.book.BookRepository;
import com.librarycodesquad.prod.global.error.exception.domain.BookNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

// AccountId = {(1 == GUEST), (2 == USER), (3 == ADMIN)}
@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AccountRepository accountRepository;

    @CsvSource({"2, 1, 2, 3, 3, 0"})
    @ParameterizedTest
    @Transactional
    public void 마이페이지_확인하기(Long accountId, Long book1, Long book2, Long book3, int size, int first) {
        //given
        Book firstBook = bookRepository.findById(book1).orElseThrow(BookNotFoundException::new);
        bookService.rentalBook(book1, accountId);
        bookService.rentalBook(book2, accountId);
        bookService.rentalBook(book3, accountId);

        //when
        AccountMyPageResponse myPage = accountService.getMyPage(accountId);
        String bookTitle = myPage.getRentalBookResponses().get(first).getTitle();
        String bookAuthor = myPage.getRentalBookResponses().get(first).getAuthor();

        //then
        assertAll(
                () -> assertThat(myPage).isNotNull(),
                () -> assertThat(myPage.getRole()).isEqualTo(LibraryRole.USER),
                () -> assertThat(myPage.getRentalBookResponses()).isNotEmpty(),
                () -> assertThat(myPage.getRentalBookResponses().size()).isEqualTo(size),
                () -> assertThat(bookTitle).isEqualTo(firstBook.getTitle()),
                () -> assertThat(bookAuthor).isEqualTo(firstBook.getAuthor())
        );
    }

    @CsvSource({"2"})
    @ParameterizedTest
    @Transactional
    public void 프로필_확인하기(Long accountId) {
        //given
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        //when
        AccountProfileResponse profile = accountService.getProfile(accountId);

        //then
        assertAll(
                () -> assertThat(profile.getName()).isEqualTo(account.getName()),
                () -> assertThat(profile.getAvatarUrl()).isEqualTo(account.getAvatarUrl())
        );
    }
}
