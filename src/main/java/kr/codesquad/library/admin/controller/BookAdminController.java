package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.domain.bookopenapi.BookData;
import kr.codesquad.library.admin.domain.bookopenapi.CreateNewBookRequest;
import kr.codesquad.library.admin.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/open_api/search_form")
    public String search() {
        return "book/books-searchform";
    }

    @GetMapping("/open_api")
    public String findBookFromOpenApi(@RequestParam("isbn") String isbn, Model model,
                                      CreateNewBookRequest createNewBookRequest) {
        BookData bookData = bookAdminService.findBookDataFromOpenApi(isbn);
        model.addAttribute("bookData", bookData);
        model.addAttribute("createNewBookRequest", createNewBookRequest);
        return "book/books-searchresult";
    }

    @PostMapping("")
    public String createNew(CreateNewBookRequest createNewBookRequest) {
        Long bookId = bookAdminService.createNewBook(createNewBookRequest);
        return "book/books-searchform";
    }
}
