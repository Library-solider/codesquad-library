package com.librarycodesquad.admin.controller;

import com.librarycodesquad.admin.domain.book.response.BookSummaryResponse;
import com.librarycodesquad.admin.domain.book.request.BookMoveRequest;
import com.librarycodesquad.admin.domain.book.response.BookDetailResponse;
import com.librarycodesquad.admin.domain.book.response.BookWithRequiredFormDataResponse;
import com.librarycodesquad.admin.domain.book.request.BookFormRequest;
import com.librarycodesquad.admin.domain.book.response.RentalBookAdminResponse;
import com.librarycodesquad.admin.service.BookAdminService;
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
        BookSummaryResponse books = bookAdminService.findAllBooks(page);
        model.addAttribute("bookSummaries", books.getBookSummaries());
        model.addAttribute("pagingProperties", books.getPagingProperties());
        return "book/books-all";
    }

    @GetMapping("/rental")
    public String findRentalBooks(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        RentalBookAdminResponse rentalBooks = bookAdminService.findRentalBooks(page);
        model.addAttribute("bookSummaries", rentalBooks.getRentalBookSummaries());
        model.addAttribute("pagingProperties", rentalBooks.getPagingProperties());
        return "book/books-rental";
    }


    @GetMapping("/search")
    public String searchBooks(@RequestParam("title") String name,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        BookSummaryResponse books = bookAdminService.searchBooks(page, name);
        model.addAttribute("bookSummaries", books.getBookSummaries());
        model.addAttribute("pagingProperties", books.getPagingProperties());
        model.addAttribute("searchTitle", name);
        return "book/books-searchall";
    }

    @PostMapping("")
    public String createNew(BookFormRequest bookFormRequest) {
        Long bookId = bookAdminService.createNewBook(bookFormRequest);
        return "redirect:/admin/books/" + bookId;
    }

    @PostMapping("/groups")
    @ResponseBody
    public void move(@RequestBody BookMoveRequest bookMoveRequest) {
        log.info("BookMoveRequest ::: {}", bookMoveRequest);
        bookAdminService.changeGroup(bookMoveRequest);
    }

    @GetMapping("/{bookId}")
    public String findDetail(@PathVariable Long bookId, Model model) {
        BookDetailResponse bookDetail = bookAdminService.findBookDetail(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", bookDetail);
        return "book/books-detail";
    }

    @GetMapping("/{bookId}/update_form")
    public String fillUpdateFormWithBookData(@PathVariable Long bookId, Model model) {
        BookWithRequiredFormDataResponse bookWithRequiredFormData = bookAdminService.findBookWithRequiredFormDataByBookId(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookData", bookWithRequiredFormData.getBookData());
        model.addAttribute("categories", bookWithRequiredFormData.getCategories());
        model.addAttribute("bookcases", bookWithRequiredFormData.getBookcases());
        return "book/books-updateform";
    }

    @PostMapping("/{bookId}/delete_form")
    public String delete(@PathVariable Long bookId) {
        bookAdminService.deleteBook(bookId);
        return "redirect:/admin/books";
    }

    @PostMapping("/{bookId}")
    public String update(@PathVariable Long bookId, BookFormRequest bookFormRequest) {
        Long updatedBookId = bookAdminService.updateBook(bookId, bookFormRequest);
        return "redirect:/admin/books/" + updatedBookId;
    }

    @GetMapping("/open_api/search_form")
    public String findBookFromOpenApi() {
        return "book/books-searchform";
    }

    @GetMapping("/open_api")
    public String createFormWithBookDataDerivedFromOpenApi(@RequestParam("isbn") String isbn, Model model) {
        BookWithRequiredFormDataResponse bookWithRequiredFormData = bookAdminService.findBookWithRequiredFormDataByIsbn(isbn);
        model.addAttribute("bookData", bookWithRequiredFormData.getBookData());
        model.addAttribute("categories", bookWithRequiredFormData.getCategories());
        model.addAttribute("bookcases", bookWithRequiredFormData.getBookcases());
        return "book/books-createform";
    }
}
