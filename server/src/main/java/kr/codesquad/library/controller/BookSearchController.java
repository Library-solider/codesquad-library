package kr.codesquad.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.codesquad.library.global.api.ApiResult.OK;

@RequiredArgsConstructor
@Api
@RestController
@RequestMapping("/v1")
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @ApiOperation(value = "메인페이지 API")
    @GetMapping("/main")
    public ResponseEntity<ApiResult<List<BooksByCategoryResponse>>> getMainBooks() {

        return new ResponseEntity<>(OK(bookSearchService.findMainBooks()), HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리별 페이지가져오기")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResult<BooksByCategoryResponse>> getCategoryBooks(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return new ResponseEntity<>(OK(bookSearchService.findByCategory(categoryId, page)), HttpStatus.OK);
    }

}
