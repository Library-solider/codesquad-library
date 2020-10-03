package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.bookopenapi.BookData;
import kr.codesquad.library.admin.domain.bookopenapi.BookDataFromOpenApi;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.global.config.properties.InterparkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;
import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookAdminService {

    private final BookAdminRepository bookAdminRepository;

    private final InterparkProperties interparkProperties;

    public BooksWithPagingResponse findAllBooks(int page) {
        Page<Book> books = bookAdminRepository.findAllWithCategory(PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE));
        List<Book> bookEntities = books.getContent();
        List<BookSummary> bookSummaries = bookEntities.stream()
                                                      .map(BookSummary::from)
                                                      .collect((Collectors.toList()));
        PagingProperties pagingProperties = PagingProperties.from(books);
        return BooksWithPagingResponse.of(bookSummaries, pagingProperties);
    }


    public void createNewBook(String isbn) {
       BookData bookData = findBookDataFromOpenApi(isbn);
    }

    public BookData findBookDataFromOpenApi(String isbn) {
        RestTemplate restTemplate = createCustomRestTemplate();
        URI bookRequestUri = createBookRequestUri(isbn);
        BookDataFromOpenApi bookDataFromOpenApi = restTemplate.getForObject(bookRequestUri, BookDataFromOpenApi.class);
        return bookDataFromOpenApi.getBookData()
                                  .stream()
                                  .findFirst()
                                  .orElseGet(BookData::new);
    }

    private int validatePageNumber(int page) {
        if (page < MINIMUM_PAGE_NUMBER) { page = MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }

    private URI createBookRequestUri(String isbn) {
        return UriComponentsBuilder.fromHttpUrl(interparkProperties.getUrl())
                                   .queryParam("key", interparkProperties.getKey())
                                   .queryParam("output", interparkProperties.getOutput())
                                   .queryParam("queryType", interparkProperties.getQueryType())
                                   .queryParam("query", isbn)
                                   .build()
                                   .toUri();
    }

    private RestTemplate createCustomRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
