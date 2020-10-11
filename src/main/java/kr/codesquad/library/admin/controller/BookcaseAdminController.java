package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.bookcase.BookcaseDataResponse;
import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.admin.service.BookcaseAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/bookcase")
public class BookcaseAdminController {

    private final BookcaseAdminService bookcaseAdminService;

    @GetMapping("")
    public String findAll(Model model) {
        List<BookcaseDetail> bookcases = bookcaseAdminService.findAllBookcases();
        model.addAttribute("bookcases", bookcases);
        return "bookcase/bookcases-all";
    }

    @GetMapping("/{bookcaseId}")
    public String findDetail(@PathVariable Long bookcaseId,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model) {
        BookcaseDataResponse bookcaseData = bookcaseAdminService.findBookcaseDataById(bookcaseId, page - 1);
        model.addAttribute("bookcaseData", bookcaseData.getBookcase());
        model.addAttribute("books", bookcaseData.getBooks());
        model.addAttribute("categories", bookcaseData.getCategories());
        model.addAttribute("bookcases", bookcaseData.getBookcases());
        model.addAttribute("pagingProperties", bookcaseData.getPagingProperties());
        return "bookcase/bookcases-books";
    }
}
