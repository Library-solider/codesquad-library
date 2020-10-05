package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.book.BookDetailResponse;
import kr.codesquad.library.admin.domain.book.BooksWithPagingResponse;
import kr.codesquad.library.admin.domain.bookopenapi.BookWithRequiredFormDataResponse;
import kr.codesquad.library.admin.domain.bookopenapi.CreateNewBookRequest;
import kr.codesquad.library.admin.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{bookId}")
    public String detail(@PathVariable Long bookId, Model model) {
        BookDetailResponse bookDetail = bookAdminService.findBook(bookId);
        model.addAttribute("book", bookDetail);
        return "book/books-detail";
    }

    @GetMapping("/open_api/search_form")
    public String searchForm() {
        return "book/books-searchform";
    }

    @GetMapping("/open_api")
    public String createFormWithBookDataDerivedFromOpenApi(@RequestParam("isbn") String isbn, Model model) {
        BookWithRequiredFormDataResponse bookWithRequiredFormData = bookAdminService.findBookWithRequiredFormData(isbn);
        model.addAttribute("bookData", bookWithRequiredFormData.getBookData());
        model.addAttribute("categories", bookWithRequiredFormData.getCategories());
        model.addAttribute("bookcases", bookWithRequiredFormData.getBookcases());
        return "book/books-createform";
    }

    @PostMapping("")
    public String createNew(CreateNewBookRequest createNewBookRequest) {
        Long bookId = bookAdminService.createNewBook(createNewBookRequest);
        return "book/books-searchform";
    }
}
