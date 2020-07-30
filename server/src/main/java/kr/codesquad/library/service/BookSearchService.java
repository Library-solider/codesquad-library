package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BooksByCategoryResponse;
import kr.codesquad.library.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public BooksByCategoryResponse findTop6BooksAndCategoryById(Long id) {
        return BooksByCategoryResponse.builder()
                .categoryId(id)
                .categoryTitle(categoryRepository.findById(id).orElseThrow(()
                        -> new IllegalStateException(" 해당하는 카테고리는 없습니다.")).getTitle())
                .books(findTop6BooksByCategory(id))
                .build();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findTop6BooksByCategory(Long id) {
        List<Book> findBookByCategory = bookRepository.findTop6bookByCategoryIdOrderByRecommendCountDesc(id);
        return findBookByCategory.stream().map(Book::toResponse).collect(Collectors.toList());
    }

}
