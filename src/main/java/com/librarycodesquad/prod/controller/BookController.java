package com.librarycodesquad.prod.controller;

import com.librarycodesquad.prod.domain.book.response.BookDetailResponse;
import com.librarycodesquad.prod.domain.book.response.BookSearchResponse;
import com.librarycodesquad.prod.domain.book.response.BooksByCategoryResponse;
import com.librarycodesquad.prod.global.api.ApiResult;
import com.librarycodesquad.prod.global.config.oauth.dto.AccountPrincipal;
import com.librarycodesquad.prod.global.config.resolver.LoginAccount;
import com.librarycodesquad.prod.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

import static com.librarycodesquad.prod.global.api.ApiResult.OK;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/v1")
public class BookController {

    private final int PAGE_MINIMUM = 1;

    private final BookService bookService;

    @GetMapping("/main")
    public ApiResult<List<BooksByCategoryResponse>> getMainBooks() {

        return OK(bookService.getMainBooks());
    }

    @GetMapping("/category/{categoryId}")
    public ApiResult<BooksByCategoryResponse> getCategoryBooks(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) Integer page) {

        return OK(bookService.getBooksByCategoryId(categoryId, page));
    }

    @GetMapping("/books/{bookId}")
    public ApiResult<BookDetailResponse> getBookDetail(@PathVariable Long bookId) {

        return OK(bookService.getBooksByBookId(bookId));
    }

    @GetMapping("/search")
    public ApiResult<BookSearchResponse> searchBooks(
            @RequestParam(value = "q") String title,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) Integer page) {

        return OK(bookService.searchBooks(title, page));
    }

    @PostMapping("/books/{bookId}")
    public ApiResult rentalBook(@PathVariable Long bookId, @LoginAccount AccountPrincipal loginAccount) {
        bookService.rentalBook(bookId, loginAccount.getId());
        return OK();
    }

    @PutMapping("/books/{bookId}")
    public ApiResult returnBook(@PathVariable Long bookId, @LoginAccount AccountPrincipal loginAccount) {
        bookService.returnBook(bookId, loginAccount.getId());
        return OK();
    }
}
