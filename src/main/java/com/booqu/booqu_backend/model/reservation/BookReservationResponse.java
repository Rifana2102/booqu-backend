package com.booqu.booqu_backend.model.reservation;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReservationResponse {

    private String bookTitle;

    private String AuthorName;

    private Date ReservationDate;

    private String status;
}