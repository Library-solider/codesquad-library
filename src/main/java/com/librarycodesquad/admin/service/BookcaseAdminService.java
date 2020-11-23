package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.common.ConstantsCoveringMagicNumber;
import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.bookcase.BookcaseAdminRepository;
import com.librarycodesquad.admin.domain.bookcase.BookcaseDataResponse;
import com.librarycodesquad.admin.domain.bookcase.BookcaseDetail;
import com.librarycodesquad.admin.domain.category.CategoryAdminRepository;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.admin.domain.book.BookAdminRepository;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.global.error.exception.admin.DeleteEntityDeniedException;
import com.librarycodesquad.prod.global.error.exception.domain.BookcaseNotFoundException;
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
        Page<Book> books = bookAdminRepository.findAllByBookcaseId(bookcaseId, PageRequest.of(validatePageNumber(page), ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE));
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
        if (page < ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER) { page = ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }
}
