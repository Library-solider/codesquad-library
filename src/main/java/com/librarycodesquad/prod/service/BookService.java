package com.librarycodesquad.prod.service;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.AccountRepository;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.book.BookRepository;
import com.librarycodesquad.prod.domain.book.response.BookDetailResponse;
import com.librarycodesquad.prod.domain.book.response.BookResponse;
import com.librarycodesquad.prod.domain.book.response.BookSearchResponse;
import com.librarycodesquad.prod.domain.book.response.BooksByCategoryResponse;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.prod.domain.category.CategoryRepository;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.domain.rental.RentalRepository;
import com.librarycodesquad.prod.global.error.exception.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.librarycodesquad.prod.domain.book.BookVO.PAGE_SIZE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final static int MAX_RENTAL_SIZE = 3;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;
    private final RentalRepository rentalRepository;

    public List<BooksByCategoryResponse> getMainBooks() {
        List<BooksByCategoryResponse> mainBooks = new ArrayList<>();
        for (int i = 0; i < categoryRepository.count(); i++) {
            mainBooks.add(findTop6BooksAndCategoryById((long) i + 1));
        }
        return mainBooks;
    }

    public BooksByCategoryResponse findTop6BooksAndCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.of(category, findTop6BooksByCategory(categoryId));
    }

    public List<BookResponse> findTop6BooksByCategory(Long categoryId) {
        List<Book> findBookByCategory = bookRepository.
                findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(categoryId);
        return findBookByCategory.stream().map(BookResponse::from).collect(Collectors.toList());
    }

    public BooksByCategoryResponse getBooksByCategoryId(Long categoryId, int page) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.of(category, findBooksByCategoryId(categoryId, page));
    }

    public List<BookResponse> findBooksByCategoryId(Long categoryId, int page) {
        Page<Book> bookPage = bookRepository.findAllByCategoryId(categoryId, getPageRequest(page));
        List<Book> bookList = bookPage.getContent();

        return bookList.stream().map(BookResponse::from).collect(Collectors.toList());
    }

    public BookDetailResponse getBooksByBookId(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Bookcase bookcase = findBook.getBookcase();
        Rental rental = rentalRepository.findByBookIdAndIsReturnedFalse(findBook.getId()).orElseGet(Rental::new);

        return BookDetailResponse.of(findBook, rental, bookcase);
    }

    public BookSearchResponse searchBooks(String searchWord, int page) {
        Page<Book> bookPage = bookRepository
                .findAllByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContaining(searchWord, searchWord, getPageRequest(page));
        List<Book> bookList = bookPage.getContent();
        List<BookResponse> bookResponseList = bookList.stream().map(BookResponse::from).collect(Collectors.toList());

        return BookSearchResponse.of(bookPage.getTotalElements(), bookResponseList);
    }

    @Transactional
    public void rentalBook(Long bookId, Long accountId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        if (!book.isAvailable()) {
            throw new OutOfBookException();
        }
        if (rentalRepository.findAllByAccountAndIsReturnedFalse(account).size() >= MAX_RENTAL_SIZE) {
            throw new MaxRentalViolationException();
        }

        book.rentalBook();
        Rental rental = Rental.createRental(book, account);
        rentalRepository.save(rental);
    }

    @Transactional
    public void returnBook(Long bookId, Long accountId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Account account =  accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        Rental rental = rentalRepository.findByBookAndAccountAndIsReturnedFalse(book, account)
                .orElseThrow(RentalNotFoundException::new);

        if (rental.isReturned()) {
            throw new RedundantRequestBookException();
        }

        rental.returnBook();
    }

    private PageRequest getPageRequest(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publicationDate");
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }
}
