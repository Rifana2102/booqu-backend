package com.booqu.booqu_backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.book.BookDetailResponse;
import com.booqu.booqu_backend.model.book.BookLoanResponse;
import com.booqu.booqu_backend.model.book.BooksResponse;
import com.booqu.booqu_backend.service.BookService;
import com.booqu.booqu_backend.service.LoanService;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(
        path = "/books",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> get(
        @RequestParam(required = false) String authorCode,
        @RequestParam(required = false) String genreCode,
        @RequestParam(required = false) String keyword
    ) {
        List<BooksResponse> response = bookService.getAllBooks(authorCode, genreCode, keyword);

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("Book list fetching success")
                                        .data(response)
                                        .build();      
    }

    @GetMapping(
    path = "/books/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookDetailResponse> getById(@PathVariable Long id, Authentication authentication) {

        boolean isLoggedIn = authentication != null && authentication.isAuthenticated();

        BookDetailResponse response = bookService.getBookById(id, isLoggedIn);

        return WebResponse.<BookDetailResponse>builder()
                        .status(true)
                        .messages("Book detail fetched successfully")
                        .data(response)
                        .build();
    }

    @GetMapping(
        path = "/books/favorites",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> getFavoriteBooks() {

        List<BooksResponse> response = bookService.getFavoriteLoanBooks();

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("Favorites Book list fetching success")
                                        .data(response)
                                        .build();      
    }

    @GetMapping(
        path = "/books/favorite-authors",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> getFavoriteAuthorBooks() {

        List<BooksResponse> response = bookService.getFavoriteAuthorBooks();

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("Favorite Authors Book list fetching success")
                                        .data(response)
                                        .build();      
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(
        path = "/books/my-libraries",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> getMyLibraryBooks(Authentication authentication) {

        List<BooksResponse> response = bookService.getMyBookHistory(authentication);

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("My Book Libraries list fetching success")
                                        .data(response)
                                        .build();      
    }

    @GetMapping("/uploads/books/pdfs/{filename:.+}")
    public ResponseEntity<Resource> getPdf(@PathVariable String filename) throws IOException {
        // Lokasi folder PDF
        Path pdfPath = Paths.get(System.getProperty("user.dir"), "booqu-backend","uploads", "books", "pdfs", filename);

        System.out.println(pdfPath);

        if (!Files.exists(pdfPath)) {
            return ResponseEntity.notFound().build();
        }

        Resource fileResource = new UrlResource(pdfPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
}
