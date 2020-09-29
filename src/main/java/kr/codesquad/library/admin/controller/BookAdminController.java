package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.book.BookSummaryResponse;
import kr.codesquad.library.admin.service.BookAdminService;
import kr.codesquad.library.domain.book.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin/books")
public class BookAdminController {

    private final BookAdminService bookAdminService;

    @GetMapping("")
    public String findAll(Model model) {
        List<BookSummaryResponse> bookSummaries = bookAdminService.findAllBooks();
        return "book/books-all";
    }

}
