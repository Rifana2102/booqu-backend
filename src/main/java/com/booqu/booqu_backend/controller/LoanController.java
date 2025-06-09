package com.booqu.booqu_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.book.BookLoanResponse;
import com.booqu.booqu_backend.model.book.BooksResponse;
import com.booqu.booqu_backend.service.BookService;
import com.booqu.booqu_backend.service.LoanService;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(
        path = "/books/my-loans",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> getMyLoanBooks(Authentication authentication) {

        List<BooksResponse> response = bookService.getMyLoanBooks(authentication);

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("My Book Loan list fetching success")
                                        .data(response)
                                        .build();      
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(
        path = "/books/{id}/loan",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookLoanResponse> loanBook(@PathVariable Long id, Authentication authentication) {

        BookLoanResponse response = loanService.loanBookById(id, authentication);

        return WebResponse.<BookLoanResponse>builder()
            .status(    true)
            .messages("Book loan success")
            .data(response)
            .build();      
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(
        path = "/books/{id}/return-loan",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookLoanResponse> returnLoanBook(@PathVariable Long id, Authentication authentication) {

        BookLoanResponse response = loanService.returnLoanBookById(id, authentication);

        return WebResponse.<BookLoanResponse>builder()
            .status(    true)
            .messages("Book Return loan success")
            .data(response)
            .build();      
    }
}
