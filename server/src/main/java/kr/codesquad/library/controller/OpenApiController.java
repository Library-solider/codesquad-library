package kr.codesquad.library.controller;

import kr.codesquad.library.domain.openapi.response.OpenApiBookDataResponse;
import kr.codesquad.library.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("/v1/books/{isbn}")
    public ResponseEntity<OpenApiBookDataResponse> getBookData(@PathVariable String isbn) {
        return new ResponseEntity<>(openApiService.requestBookData(isbn), HttpStatus.OK);
    }
}
