package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.request.BookMoveRequest;
import kr.codesquad.library.admin.domain.book.response.BookDetailResponse;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.response.BookSummaryResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    public BookSummaryResponse findAllBooks(int page) {
        Page<Book> books = bookAdminRepository.findAllFetch(PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE));
        List<Book> bookEntities = books.getContent();
        List<BookSummary> bookSummaries = bookEntities.stream()
                                                      .map(BookSummary::from)
                                                      .collect((Collectors.toList()));
        PagingProperties pagingProperties = PagingProperties.from(books);
        return BookSummaryResponse.of(bookSummaries, pagingProperties);
    }


    @Transactional
    public Long createNewBook(BookFormRequest bookFormRequest) {
        Category category = categoryAdminRepository.findById(bookFormRequest.getCategoryId())
                                                   .orElseThrow(CategoryNotFoundException::new);
        Bookcase bookcase = bookcaseAdminRepository.findById(bookFormRequest.getBookcaseId())
                                                   .orElseThrow(BookcaseNotFoundException::new);
        Book newBook = bookAdminRepository.save(Book.of(bookFormRequest, category, bookcase));
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
            Category category = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            books.forEach(book -> book.changeCategory(category));
        });
        Optional.ofNullable(bookMoveRequest.getBookcaseId()).ifPresent(bookcaseId -> {
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

    @Transactional
    public void deleteBook(Long bookId) {
        bookAdminRepository.deleteById(bookId);
    }

    public BookSummaryResponse searchBooks(int page, String title) {
        PageRequest pageRequest = PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE);
        Page<Book> books = bookAdminRepository.findAllByTitleContainingIgnoreCase(pageRequest, title);
        PagingProperties pagingProperties = PagingProperties.from(books);
        List<Book> bookEntities = books.getContent();
        List<BookSummary> bookSummaries = bookEntities.stream()
                                                      .map(BookSummary::from)
                                                      .collect(Collectors.toList());
        return BookSummaryResponse.of(bookSummaries, pagingProperties);
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
