package kr.codesquad.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.config.resolver.LoginAccount;
import kr.codesquad.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

import static kr.codesquad.library.global.api.ApiResult.OK;

@RequiredArgsConstructor
@Api
@Validated
@RestController
@RequestMapping("/v1")
public class BookController {

    private final int PAGE_MINIMUM = 1;

    private final BookService bookService;

    @ApiOperation(value = "메인페이지")
    @GetMapping("/main")
    public ApiResult<List<BooksByCategoryResponse>> getMainBooks() {

        return OK(bookService.findMainBooks());
    }

    @ApiOperation(value = "카테고리별 페이지가져오기")
    @GetMapping("/category/{categoryId}")
    public ApiResult<BooksByCategoryResponse> getCategoryBooks(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) int page) {

        return OK(bookService.findByCategory(categoryId, page));
    }

    @ApiOperation(value = "도서 상세페이지")
    @GetMapping("/books/{bookId}")
    public ApiResult<BookDetailResponse> getBookDetail(@PathVariable Long bookId) {

        return OK(bookService.findByBookId(bookId));
    }

    @ApiOperation(value = "도서 검색페이지")
    @GetMapping("/search")
    public ApiResult<BookSearchResponse> searchBooks(
            @RequestParam(value = "q") String title,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) int page) {

        return OK(bookService.searchBooks(title, page));
    }

    @ApiOperation(value = "도서 렌탈")
    @PostMapping("/books/{bookId}")
    public ApiResult rentalBook(@PathVariable Long bookId, @LoginAccount AccountPrincipal loginAccount) {
        bookService.rentalBookByUser(bookId, loginAccount.getId());
        return OK();
    }

    @ApiOperation(value = "도서 반납")
    @PutMapping("/books/{bookId}")
    public ApiResult returnBook(@PathVariable Long bookId, @LoginAccount AccountPrincipal loginAccount) {
        bookService.returnBookByUser(bookId, loginAccount.getId());
        return OK();
    }
}
