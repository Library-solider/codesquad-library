package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookcaseAdminService {

    private final BookcaseAdminRepository bookcaseAdminRepository;

    public List<BookcaseDetail> findAllBookcases() {
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        return bookcases.stream()
                        .map(bookcase -> {
                                List<Book> books = bookcase.getBooks();
                                return BookcaseDetail.of(bookcase, books.size());
                        })
                        .collect(Collectors.toList());
    }
}
