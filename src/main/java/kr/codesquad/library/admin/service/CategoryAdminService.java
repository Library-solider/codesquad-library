package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.book.BookAdminRepository;
import kr.codesquad.library.admin.domain.bookcase.BookcaseAdminRepository;
import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.admin.domain.category.CategoryDataResponse;
import kr.codesquad.library.admin.domain.category.CategoryDetail;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class CategoryAdminService {

    private final CategoryAdminRepository categoryAdminRepository;
    private final BookAdminRepository bookAdminRepository;
    private final BookcaseAdminRepository bookcaseAdminRepository;

    public List<CategoryDetail> findAllCategory() {
        List<Category> categories = categoryAdminRepository.findAll();
        return categories.stream()
                         .map(category -> {
                             List<Book> books = category.getBooks();
                             return CategoryDetail.of(category, books);
                        })
                        .collect(Collectors.toList());
    }

    public CategoryDataResponse findCategoryDataById(Long categoryId, int page) {
        Category category = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Page<Book> books = bookAdminRepository.findAllByCategoryId(categoryId, PageRequest.of(page, ADMIN_PAGE_SIZE));
        List<Category> categories = categoryAdminRepository.findAll();
        List<Bookcase> bookcases = bookcaseAdminRepository.findAll();
        return CategoryDataResponse.builder()
                                   .category(category)
                                   .books(books.getContent())
                                   .categories(categories)
                                   .bookcases(bookcases)
                                   .pagingProperties(PagingProperties.from(books))
                                   .build();
    }
}
