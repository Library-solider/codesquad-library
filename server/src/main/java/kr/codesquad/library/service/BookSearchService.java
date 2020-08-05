package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.category.CategoryRepository;
import kr.codesquad.library.domain.rental.Rental;
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
public class BookSearchService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BooksByCategoryResponse> findMainBooks() {
        List<BooksByCategoryResponse> mainBooks = new ArrayList<>();
        for (int i = 0; i < categoryRepository.count(); i++) {
            mainBooks.add(findTop6BooksAndCategoryById((long) i + 1));
        }
        return mainBooks;
    }

    @Transactional(readOnly = true)
    public BooksByCategoryResponse findTop6BooksAndCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(category.getTitle())
                .bookCount(category.getBooks().size())
                .books(findTop6BooksByCategory(categoryId))
                .build();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findTop6BooksByCategory(Long categoryId) {
        List<Book> findBookByCategory = bookRepository.
                findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(categoryId);
        return findBookByCategory.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BooksByCategoryResponse findByCategory(Long categoryId, int page) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(category.getTitle())
                .bookCount(category.getBooks().size())
                .books(findByCategoryIdBooks(categoryId, page))
                .build();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findByCategoryIdBooks(Long categoryId, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publicationDate");
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE, sort);
        Page<Book> bookPage = bookRepository.findByCategoryId(categoryId, pageRequest);
        List<Book> bookList = bookPage.getContent();

        return bookList.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDetailResponse findByBookId(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Rental rental = findBook.getRentals().get(0);

        return BookDetailResponse.of(findBook, rental);
    }

}
