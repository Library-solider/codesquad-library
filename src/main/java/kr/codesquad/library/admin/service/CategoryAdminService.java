package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.admin.domain.category.CategoryDetail;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryAdminService {

    private final CategoryAdminRepository categoryAdminRepository;

    public List<CategoryDetail> findAllCategory() {
        List<Category> categories = categoryAdminRepository.findAll();
        return categories.stream()
                         .map(category -> {
                             List<Book> books = category.getBooks();
                             return CategoryDetail.of(category, books);
                        })
                        .collect(Collectors.toList());
    }
}
