package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.util.PagingProperties;
import kr.codesquad.library.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookAdminService {

    private final BookAdminRepository bookAdminRepository;

    public BooksWithPagingResponse findAllBooks(int page) {
        Page<Book> books = bookAdminRepository.findAllWithCategory(PageRequest.of(page - 1, 10));
        List<Book> bookEntities = books.getContent();
        List<BookSummary> bookSummaries = bookEntities.stream()
                                                      .map(BookSummary::from)
                                                      .collect((Collectors.toList()));
        PagingProperties pagingProperties = PagingProperties.from(books);
        return BooksWithPagingResponse.of(bookSummaries, pagingProperties);
    }
}
