package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.admin.domain.category.CategoryDetailResponse;
import kr.codesquad.library.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryAdminService {

    private final CategoryAdminRepository categoryAdminRepository;

    public List<CategoryDetailResponse> findAllCategory() {
        List<Category> categories = categoryAdminRepository.findAll();
        return categories.stream()
                         .map(CategoryDetailResponse::from)
                         .collect(Collectors.toList());
    }
}
