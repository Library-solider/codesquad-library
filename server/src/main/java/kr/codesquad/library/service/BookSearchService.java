package kr.codesquad.library.service;

import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.MainBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookSearchService {

    private final BookRepository books;

}
