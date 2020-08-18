package kr.codesquad.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.service.BookSearchService;
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
public class BookSearchController {

    private final int PAGE_MINIMUM = 1;

    private final BookSearchService bookSearchService;

    @ApiOperation(value = "메인페이지")
    @GetMapping("/main")
    public ApiResult<List<BooksByCategoryResponse>> getMainBooks() {

        return OK(bookSearchService.findMainBooks());
    }

    @ApiOperation(value = "카테고리별 페이지가져오기")
    @GetMapping("/category/{categoryId}")
    public ApiResult<BooksByCategoryResponse> getCategoryBooks(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) int page) {

        return OK(bookSearchService.findByCategory(categoryId, page));
    }

    @ApiOperation(value = "도서 상세페이지")
    @GetMapping("/books/{bookId}")
    public ApiResult<BookDetailResponse> getBookDetail(@PathVariable Long bookId) {

        return OK(bookSearchService.findByBookId(bookId));
    }

    @ApiOperation(value = "도서 검색페이지")
    @GetMapping("/search")
    public ApiResult<BookSearchResponse> searchBooks(
            @RequestParam(value = "q") String title,
            @RequestParam(value = "page", defaultValue = "1") @Min(PAGE_MINIMUM) int page) {

        return OK(bookSearchService.searchBooks(title, page));
    }
}
