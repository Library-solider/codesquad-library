package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.openapi.response.OpenApiBookDataResponse;
import kr.codesquad.library.domain.openapi.response.OpenApiResponse;
import kr.codesquad.library.global.config.properties.vo.OpenApiParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiParameter apiParameter;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public OpenApiBookDataResponse requestBookData(String isbn) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(apiParameter.getUrl())
                                                .queryParam("key", apiParameter.getKey())
                                                .queryParam("output", apiParameter.getOutput())
                                                .queryParam("queryType", apiParameter.getQueryType())
                                                .queryParam("query", isbn)
                                                .build(false)
                                                .toString();

        OpenApiResponse items = restTemplate.getForObject(requestUrl, OpenApiResponse.class);
        return items.getItem().get(0);
    }

    @Transactional
    public void initBookData() {
        List<Book> allBooks = bookRepository.findAll();
        allBooks.stream()
                .filter(book -> (book.getIsbn().length() > 7))
                .forEach(filteredBook -> addImageUrlAndDescription(filteredBook));
    }

    private void addImageUrlAndDescription(Book book) {
        OpenApiBookDataResponse bookData = requestBookData(book.getIsbn());
        book.addImageAndDescription(bookData.getImageUrl(), bookData.getDescription());
        bookRepository.save(book);
    }
}
