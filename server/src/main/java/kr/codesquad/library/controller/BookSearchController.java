package kr.codesquad.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kr.codesquad.library.global.api.ApiResult.OK;

@RequiredArgsConstructor
@Api
@RestController
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @ApiOperation(value = "메인페이지 API")
    @GetMapping("/books/main")
    public ApiResult<List<BooksByCategoryResponse>> getMainBooks() {

        return OK(bookSearchService.findMainBooks());
    }
}
