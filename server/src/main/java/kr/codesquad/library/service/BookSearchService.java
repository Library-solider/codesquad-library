package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(categoryRepository.findById(categoryId).orElseThrow(()
                        -> new IllegalStateException("해당하는 " + categoryId + "의 카테고리는 없습니다.")).getTitle())
                .bookCount(bookRepository.countByCategoryId(categoryId))
                .books(findTop6BooksByCategory(categoryId))
                .build();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findTop6BooksByCategory(Long categoryId) {
        List<Book> findBookByCategory = bookRepository.
                findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(categoryId);
        return findBookByCategory.stream().map(BookResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public BooksByCategoryResponse findByCategory(Long categoryId, int page) {
        return BooksByCategoryResponse.builder()
                .categoryId(categoryId)
                .categoryTitle(categoryRepository.findById(categoryId).orElseThrow(()
                        -> new IllegalStateException("해당하는 " + categoryId + "의 카테고리는 없습니다.")).getTitle())
                .bookCount(bookRepository.countByCategoryId(categoryId))
                .books(findByCategoryIdBooks(categoryId, page))
                .build();
    }

    @Transactional
    public List<BookResponse> findByCategoryIdBooks(Long categoryId, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
        Page<Book> bookPage = bookRepository.findByCategoryIdOrderByPublicationDateDesc(categoryId, pageRequest);
        List<Book> bookList = bookPage.getContent();

        return bookList.stream().map(BookResponse::of).collect(Collectors.toList());
    }
}
