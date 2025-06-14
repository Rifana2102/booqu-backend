package com.booqu.booqu_backend.model.book;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoanResponse {

    private String bookTitle;

    private String authorName;

    private LocalDate loanDate;
    
    private LocalDate dueDate;

     private LocalDate returnDate;

     private String status;
}
