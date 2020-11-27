package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.book.BookAdminRepository;
import com.librarycodesquad.admin.domain.book.BookData;
import com.librarycodesquad.admin.domain.book.BookSummary;
import com.librarycodesquad.admin.domain.book.RentalBookSummary;
import com.librarycodesquad.admin.domain.book.request.BookFormRequest;
import com.librarycodesquad.admin.domain.book.request.BookMoveRequest;
import com.librarycodesquad.admin.domain.book.response.BookDetailResponse;
import com.librarycodesquad.admin.domain.book.response.BookSummaryResponse;
import com.librarycodesquad.admin.domain.book.response.BookWithRequiredFormDataResponse;
import com.librarycodesquad.admin.domain.book.response.RentalBookAdminResponse;
import com.librarycodesquad.admin.domain.bookcase.BookcaseAdminRepository;
import com.librarycodesquad.admin.domain.bookopenapi.BookDataFromOpenApi;
import com.librarycodesquad.admin.domain.category.CategoryAdminRepository;
import com.librarycodesquad.admin.domain.rental.RentalAdminRepository;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.global.config.RestTemplateConfig;
import com.librarycodesquad.prod.global.config.properties.InterparkProperties;
import com.librarycodesquad.prod.global.error.exception.domain.BookNotFoundException;
import com.librarycodesquad.prod.global.error.exception.domain.BookcaseNotFoundException;
import com.librarycodesquad.prod.global.error.exception.domain.CategoryNotFoundException;
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

import static com.librarycodesquad.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;
import static com.librarycodesquad.admin.common.ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookAdminService {

    private final BookAdminRepository bookAdminRepository;
    private final CategoryAdminRepository categoryAdminRepository;
    private final BookcaseAdminRepository bookcaseAdminRepository;
    private final RentalAdminRepository rentalAdminRepository;
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

    public RentalBookAdminResponse findRentalBooks(int page) {
        Page<Rental> rentals = rentalAdminRepository
                .findAllByIsReturnedFalse(PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE));
        List<RentalBookSummary> rentalBooks = rentals.stream()
                                                       .map(RentalBookSummary::from)
                                                       .collect(Collectors.toList());
        PagingProperties pagingProperties = PagingProperties.from(rentals);
        return RentalBookAdminResponse.of(rentalBooks, pagingProperties);
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
        Category assignedCategory = book.getCategory();
        Bookcase assignedBookcase = book.getBookcase();
        BookData bookData = BookData.of(book, assignedCategory, assignedBookcase);
        return createRequiredFormData(bookData);
    }

    public BookDetailResponse findBookDetail(Long bookId) {
        Book book = bookAdminRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        List<Rental> rentals = rentalAdminRepository.findAllByBookIdAndIsReturnedFalse(bookId);
        return BookDetailResponse.of(book, rentals);
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
        if (page < MINIMUM_PAGE_NUMBER) {
            page = MINIMUM_PAGE_NUMBER;
        }
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
