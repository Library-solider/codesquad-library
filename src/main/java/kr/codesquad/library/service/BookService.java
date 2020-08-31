package kr.codesquad.library.service;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.category.CategoryRepository;
import kr.codesquad.library.domain.rental.Rental;
import kr.codesquad.library.domain.rental.RentalRepository;
import kr.codesquad.library.domain.rental.firstclass.Rentals;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.domain.book.BookVO.PAGE_SIZE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;
    private final RentalRepository rentalRepository;

    public List<BooksByCategoryResponse> findMainBooks() {
        List<BooksByCategoryResponse> mainBooks = new ArrayList<>();
        for (int i = 0; i < categoryRepository.count(); i++) {
            mainBooks.add(findTop6BooksAndCategoryById((long) i + 1));
        }
        return mainBooks;
    }

    public BooksByCategoryResponse findTop6BooksAndCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(category.getTitle())
                .bookCount(category.getBooks().size())
                .books(findTop6BooksByCategory(categoryId))
                .build();
    }

    public List<BookResponse> findTop6BooksByCategory(Long categoryId) {
        List<Book> findBookByCategory = bookRepository.
                findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(categoryId);
        return findBookByCategory.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    public BooksByCategoryResponse findByCategory(Long categoryId, int page) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(category.getTitle())
                .bookCount(category.getBooks().size())
                .books(findByCategoryIdBooks(categoryId, page))
                .build();
    }

    public List<BookResponse> findByCategoryIdBooks(Long categoryId, int page) {
        Page<Book> bookPage = bookRepository.findByCategoryId(categoryId, getPageRequest(page));
        List<Book> bookList = bookPage.getContent();

        return bookList.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    public BookDetailResponse findByBookId(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Rentals rentals = Rentals.of(findBook.getRentals());
        Rental rental = rentals.findByBook(findBook);

        return BookDetailResponse.of(findBook, rental);
    }

    public BookSearchResponse searchBooks(String searchWord, int page) {
        List<BookResponse> bookResponseList = searchBooksList(searchWord);
        bookResponseList.sort(Comparator.comparing(BookResponse::getPublicationDate).reversed());
        return BookSearchResponse.builder()
                .bookCount(bookResponseList.size())
                .books(getBookListPage(bookResponseList, page))
                .build();
    }

    public List<BookResponse> searchBooksList(String searchWord) {
        List<Book> findBookByTitle =  bookRepository.findByTitleIgnoreCaseContaining(searchWord);
        List<Book> bookList = new ArrayList<>(findBookByTitle);

        List<Book> findBookByAuthor = bookRepository.findByAuthorIgnoreCaseContaining(searchWord);
        bookList.addAll(findBookByAuthor);

        return bookList.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void rentalBookByUser(Long bookId, AccountPrincipal loginAccount) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AcceptPendingException::new);
        book.rentalOrReturnBook();
        rentalRepository.save(Rental.create(book, account));
    }

    private PageRequest getPageRequest(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publicationDate");
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }

    private List<BookResponse> getBookListPage(List<BookResponse> bookList, int page) {
        int skipPage = (page - 1) * 20;
        return bookList.stream().skip(skipPage).limit(20).collect(Collectors.toList());
    }
}
