package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDataResponse;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.global.error.exception.admin.DeleteEntityDeniedException;
import kr.codesquad.library.global.error.exception.domain.BookcaseNotFoundException;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;
import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookcaseAdminService {

    private final BookcaseAdminRepository bookcaseAdminRepository;
    private final BookAdminRepository bookAdminRepository;
    private final CategoryAdminRepository categoryAdminRepository;

    public List<BookcaseDetail> findAllBookcases() {
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        return bookcases.stream()
                        .map(bookcase -> {
                                List<Book> books = bookcase.getBooks();
                                return BookcaseDetail.of(bookcase, books.size());
                        })
                        .collect(Collectors.toList());
    }

    public BookcaseDataResponse findBookcaseDataById(Long bookcaseId, int page) {
        Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        Page<Book> books = bookAdminRepository.findAllByBookcaseId(bookcaseId, PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE));
        List<Category> categories = categoryAdminRepository.findAll();
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        return BookcaseDataResponse.builder()
                                   .bookcase(bookcase)
                                   .books(books.getContent())
                                   .categories(categories)
                                   .bookcases(bookcases)
                                   .pagingProperties(PagingProperties.from(books))
                                   .build();
    }

    @Transactional
    public void updateBookcaseLocation(Long bookcaseId, String location) {
        Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        bookcase.changeLocation(location);
    }

    @Transactional
    public Long createNewBookcase(String location) {
        Bookcase bookcase = Bookcase.from(location);
        Bookcase newBookcase = bookcaseAdminRepository.save(bookcase);
        return newBookcase.getId();
    }

    @Transactional
    public void deleteBookcase(Long bookcaseId) {
        Bookcase bookcase = bookcaseAdminRepository.findById(bookcaseId).orElseThrow(BookcaseNotFoundException::new);
        if (bookcase.hasAnyBooks()) { throw new DeleteEntityDeniedException(); }
        bookcaseAdminRepository.delete(bookcase);
    }

    private int validatePageNumber(int page) {
        if (page < MINIMUM_PAGE_NUMBER) { page = MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }
}
