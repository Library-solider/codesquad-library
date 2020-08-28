package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.category.CategoryRepository;
import kr.codesquad.library.domain.rental.Rental;
import kr.codesquad.library.domain.rental.firstclass.Rentals;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.domain.book.BookVO.PAGE_SIZE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookSearchService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

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
        Rental rental = rentals.find(findBook);

        return BookDetailResponse.of(findBook, rental);
    }

    public BookSearchResponse searchBooks(String title, int page) {
        List<BookResponse> bookResponseList = searchBooksList(title, page);
        return BookSearchResponse.builder()
                .bookCount(bookResponseList.size())
                .books(bookResponseList)
                .build();
    }

    public List<BookResponse> searchBooksList(String title, int page) {
        Page<Book> bookPage = bookRepository.findByTitleIgnoreCaseContaining(title, getPageRequest(page));
        List<Book> bookList = bookPage.getContent();

        return bookList.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publicationDate");
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }
}
