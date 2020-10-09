package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.category.CategoryDetail;
import kr.codesquad.library.admin.service.CategoryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @GetMapping("")
    public String findAll(Model model) {
        List<CategoryDetail> categories = categoryAdminService.findAllCategory();
        model.addAttribute("categories", categories);
        return "category/categories-all";
    }
}
