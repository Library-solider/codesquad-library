package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.category.CategoryDataResponse;
import kr.codesquad.library.admin.domain.category.CategoryDetail;
import kr.codesquad.library.admin.service.BookAdminService;
import kr.codesquad.library.admin.service.CategoryAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("")
    public String createNew(String title) {
        categoryAdminService.createNewCategory(title);
        return "redirect:/admin/category";
    }

    @PostMapping("/{categoryId}/title")
    public String update(@PathVariable Long categoryId, String title) {
        categoryAdminService.updateCategoryTitle(categoryId, title);
        return "redirect:/admin/category";
    }

    @GetMapping("/{categoryId}")
    public String findData(@PathVariable Long categoryId,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           Model model) {
        CategoryDataResponse categoryData = categoryAdminService.findCategoryDataById(categoryId, page - 1);
        model.addAttribute("categoryData", categoryData.getCategory());
        model.addAttribute("books", categoryData.getBooks());
        model.addAttribute("categories", categoryData.getCategories());
        model.addAttribute("bookcases", categoryData.getBookcases());
        model.addAttribute("pagingProperties", categoryData.getPagingProperties());
        return "category/categories-books";
    }

    @PostMapping("/{categoryId}")
    public String delete(@PathVariable Long categoryId) {
        categoryAdminService.deleteCategory(categoryId);
        return "redirect:/admin/category";
    }
}
