package kr.codesquad.library.controller;

import kr.codesquad.library.domain.book.response.MainBookResponse;
import kr.codesquad.library.global.api.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookSearchController {

    @GetMapping("/books/main")
    public ResponseEntity<ApiResult<MainBookResponse>> getMainBooks() {

        return null;
    }
}
