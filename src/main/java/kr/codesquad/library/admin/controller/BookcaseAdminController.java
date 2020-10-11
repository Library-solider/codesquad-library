package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.bookcase.BookcaseDetail;
import kr.codesquad.library.admin.service.BookcaseAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
