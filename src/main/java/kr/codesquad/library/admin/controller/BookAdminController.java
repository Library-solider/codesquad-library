package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.domain.bookopenapi.BookData;
import kr.codesquad.library.admin.service.BookAdminService;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin/books")
public class BookAdminController {

    private final BookAdminService bookAdminService;

    @GetMapping("")
    public String findAll(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        BooksWithPagingResponse books = bookAdminService.findAllBooks(page);
        model.addAttribute("bookSummaries", books.getBookSummaries());
        model.addAttribute("pagingProperties", books.getPagingProperties());
        return "book/books-all";
    }

    @GetMapping("/open_api")
    public String findBookFromOpenApi(@RequestParam("isbn") String isbn, Model model) {
        BookData bookData = bookAdminService.findBookDataFromOpenApi(isbn);
        model.addAttribute("bookData", bookData);
        return "book/books-form";
    }

    @PostMapping("")
    public String createNew() {
        bookAdminService.createNewBook();
        return "book/books-create";
    }
}
