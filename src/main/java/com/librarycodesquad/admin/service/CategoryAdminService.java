package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.common.ConstantsCoveringMagicNumber;
import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.bookcase.BookcaseAdminRepository;
import com.librarycodesquad.admin.domain.category.CategoryAdminRepository;
import com.librarycodesquad.admin.domain.category.CategoryDataResponse;
import com.librarycodesquad.admin.domain.category.CategoryDetail;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.prod.global.error.exception.domain.CategoryNotFoundException;
import com.librarycodesquad.admin.domain.book.BookAdminRepository;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.global.error.exception.admin.DeleteEntityDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Page<Book> books = bookAdminRepository.findAllByCategoryId(categoryId, PageRequest.of(validatePageNumber(page), ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE));
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

    @Transactional
    public Long createNewCategory(String title) {
        Category category = Category.from(title);
        Category newCategory = categoryAdminRepository.save(category);
        return newCategory.getId();
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        if (category.hasAnyBooks()) { throw new DeleteEntityDeniedException(); }
        categoryAdminRepository.delete(category);
    }


    @Transactional
    public Long updateCategoryTitle(Long categoryId, String title) {
        Category category = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        category.changeTitle(title);
        return category.getId();
    }

    private int validatePageNumber(int page) {
        if (page < ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER) { page = ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }
}
