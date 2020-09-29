package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.book.BookSummaryResponse;
import kr.codesquad.library.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookAdminService {

    private final BookAdminRepository bookAdminRepository;

    public List<BookSummaryResponse> findAllBooks() {
        List<Book> books = bookAdminRepository.findAllWithCategory();
        return books.stream()
                    .map(BookSummaryResponse::from)
                    .collect(Collectors.toList());
    }
}
