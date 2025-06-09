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
import com.booqu.booqu_backend.model.book.BooksResponse;
import com.booqu.booqu_backend.model.reservation.BookReservationResponse;
import com.booqu.booqu_backend.service.BookService;
import com.booqu.booqu_backend.service.ReservationService;

@RestController
@RequestMapping("/api")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;

    @Autowired BookService bookService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(
        path = "/books/my-reservations",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<BooksResponse>> getMyReservationBooks(Authentication authentication) {

        List<BooksResponse> response = bookService.getMyReservedBooks(authentication);

        return WebResponse.<List<BooksResponse>>builder()
                                        .status(    true)
                                        .messages("My Book Reservation list fetching success")
                                        .data(response)
                                        .build();      
    }

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(
        path = "/books/{id}/reservation",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookReservationResponse> reserveBook(@PathVariable Long id, Authentication authentication) {

        BookReservationResponse response = reservationService.reserveBookById(id, authentication);

        return WebResponse.<BookReservationResponse>builder()
            .status(    true)
            .messages("Book Reservation success")
            .data(response)
            .build();      
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(
        path = "/books/{id}/cancel-reservation",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookReservationResponse> cancelReserveBook(@PathVariable Long id, Authentication authentication) {

        BookReservationResponse response = reservationService.cancelReserveBookById(id, authentication);

        return WebResponse.<BookReservationResponse>builder()
            .status(    true)
            .messages("Reservation cancelled successfully")
            .data(response)
            .build();      
    }
}
