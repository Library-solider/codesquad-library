package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.request.BookMoveRequest;
import kr.codesquad.library.admin.domain.book.response.BookDetailResponse;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.response.BooksWithPagingResponse;
import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.book.BookData;
import kr.codesquad.library.admin.domain.bookopenapi.BookDataFromOpenApi;
import kr.codesquad.library.admin.domain.book.request.BookFormRequest;
import kr.codesquad.library.admin.domain.book.response.BookWithRequiredFormDataResponse;
import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.global.config.RestTemplateConfig;
import kr.codesquad.library.global.config.properties.InterparkProperties;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import kr.codesquad.library.global.error.exception.domain.BookcaseNotFoundException;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;
import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookAdminService {

    private final BookAdminRepository bookAdminRepository;
    private final CategoryAdminRepository categoryAdminRepository;
    private final BookcaseAdminRepository bookcaseAdminRepository;
    private final RestTemplateConfig restTemplateConfig;
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


    @Transactional
    public Long createNewBook(BookFormRequest bookFormRequest) {
        Category category = categoryAdminRepository.findById(bookFormRequest.getCategoryId())
                                                   .orElseThrow(CategoryNotFoundException::new);
        Bookcase bookcase = bookcaseAdminRepository.findById(bookFormRequest.getBookcaseId())
                                                   .orElseThrow(BookcaseNotFoundException::new);
        Book newBook = bookAdminRepository.save(Book.of(bookFormRequest, category, bookcase));
        log.debug("New Book ID ::: {}", newBook.getId());
        return newBook.getId();
    }

    @Transactional
    public Long updateBook(Long bookId, BookFormRequest bookFormRequest) {
        Book book = bookAdminRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Category category = categoryAdminRepository.findById(bookFormRequest.getCategoryId())
                                                   .orElseThrow(CategoryNotFoundException::new);
        Bookcase bookcase = bookcaseAdminRepository.findById(bookFormRequest.getBookcaseId())
                                                   .orElseThrow(BookcaseNotFoundException::new);
        return book.updateBook(bookFormRequest, category, bookcase);
    }

    public BookWithRequiredFormDataResponse findBookWithRequiredFormDataByIsbn(String isbn) {
        BookData bookData = findBookDataFromOpenApi(isbn);
        return createRequiredFormData(bookData);
    }

    public BookWithRequiredFormDataResponse findBookWithRequiredFormDataByBookId(Long bookId) {
        Book book = bookAdminRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        BookData bookData = BookData.from(book);
        return createRequiredFormData(bookData);
    }

    public BookDetailResponse findBookDetail(Long bookId) {
        Book book = bookAdminRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return BookDetailResponse.from(book);
    }

    @Transactional
    public void changeGroup(BookMoveRequest bookMoveRequest) {
        List<Book> books = bookAdminRepository.findAllByIdIn(bookMoveRequest.getBookIds());
        Optional.ofNullable(bookMoveRequest.getCategoryId()).ifPresent(categoryId -> {
            log.debug("Come To Map Category :::");
            Category category = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            books.forEach(book -> book.changeCategory(category));
        });
        Optional.ofNullable(bookMoveRequest.getBookcaseId()).ifPresent(bookcaseId -> {
            log.debug("Come To Map Bookcase :::");
            Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
            books.forEach(book -> book.changeBookcase(bookcase));
        });
    }

    public BookData findBookDataFromOpenApi(String isbn) {
        RestTemplate restTemplate = restTemplateConfig.restTemplate();
        URI bookRequestUri = createBookRequestUri(isbn);
        BookDataFromOpenApi bookDataFromOpenApi = restTemplate.getForObject(bookRequestUri, BookDataFromOpenApi.class);
        return bookDataFromOpenApi.getBookData()
                                  .stream()
                                  .findFirst()
                                  .orElseGet(BookData::new);
    }

    private BookWithRequiredFormDataResponse createRequiredFormData(BookData bookData) {
        List<Category> categories = categoryAdminRepository.findAll();
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        return BookWithRequiredFormDataResponse.of(bookData, categories, bookcases);
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
}
