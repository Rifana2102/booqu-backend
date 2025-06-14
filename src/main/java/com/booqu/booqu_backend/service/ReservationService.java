package com.booqu.booqu_backend.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.BookEntity;
import com.booqu.booqu_backend.entity.BookStockEntity;
import com.booqu.booqu_backend.entity.LoanEntity;
import com.booqu.booqu_backend.entity.MasterTransactionTypeEntity;
import com.booqu.booqu_backend.entity.ReservationEntity;
import com.booqu.booqu_backend.entity.TransactionEntity;
import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.model.reservation.BookReservationResponse;
import com.booqu.booqu_backend.repository.BookRepository;
import com.booqu.booqu_backend.repository.BookStockRepository;
import com.booqu.booqu_backend.repository.LoanRepository;
import com.booqu.booqu_backend.repository.ReservationRepository;
import com.booqu.booqu_backend.repository.TransactionRepository;
import com.booqu.booqu_backend.repository.TransactionTypeRepository;
import com.booqu.booqu_backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final BookLoanMaintenanceService bookLoanMaintenanceService;

    @Transactional
    public BookReservationResponse reserveBookById(Long bookId, Authentication authentication) {
        String username = authentication.getName();
        String reservationStatus = "";

        // Step 0: Run global auto-return and reservation processing
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        // Step 1: Get current user
        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Step 2: Get book and stock
        BookEntity book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        BookStockEntity bookStock = bookStockRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book stock not found"));

        // Step 3: Check if the user already has an active reservation (not yet loaned)
        boolean hasUnloanedReservation = reservationRepository
            .existsByUserAndBookAndIsLoanedFalseAndIsCancelledFalse(currentUser, book);
        if (hasUnloanedReservation) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have a pending reservation for this book");
        }

        boolean hasActiveLoan = loanRepository.existsByUserAndBookAndIsReturnedFalse(currentUser, book);
        if (hasActiveLoan) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have an active loan for this book");
        }

        // Step 4: Prepare transaction types
        MasterTransactionTypeEntity loanType = transactionTypeRepository.findByCode("LOAN")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type LOAN not found"));
        MasterTransactionTypeEntity reservedType = transactionTypeRepository.findByCode("RESERVED")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type RESERVED not found"));

        Date now = new Date();

        // Step 5: Create reservation record
        ReservationEntity reservation = ReservationEntity.builder()
            .book(book)
            .user(currentUser)
            .reservationDate(now)
            .isLoaned(false)
            .isCancelled(false)
            .createdAt(now)
            .createdBy(username)
            .updatedAt(now)
            .updatedBy(username)
            .build();
        reservationRepository.save(reservation);

        // Step 6: Create RESERVED transaction
        TransactionEntity reservedTransaction = TransactionEntity.builder()
            .book(book)
            .user(currentUser)
            .transactionDate(now)
            .transactionType(reservedType)
            .build();
        transactionRepository.save(reservedTransaction);

        reservationStatus = reservedType.getCode();

        // Step 7: If stock available, directly loan the book
        if (bookStock.getBookAvailable() != null && bookStock.getBookAvailable() > 0) {
            LocalDate dueDate = LocalDate.now().plusDays(5);

            // Create loan
            LoanEntity loan = LoanEntity.builder()
                .book(book)
                .user(currentUser)
                .loanDate(LocalDate.now())
                .dueDate(dueDate)
                .isReturned(false)
                .createdAt(now)
                .createdBy(username)
                .updatedAt(now)
                .updatedBy(username)
                .build();
            loanRepository.save(loan);

            // Create LOAN transaction
            TransactionEntity loanTransaction = TransactionEntity.builder()
                .book(book)
                .user(currentUser)
                .transactionDate(now)
                .transactionType(loanType)
                .build();
            transactionRepository.save(loanTransaction);

            // Mark reservation as loaned
            reservation.setIsLoaned(true);
            reservation.setUpdatedAt(now);
            reservation.setUpdatedBy(username);
            reservationRepository.save(reservation);

            // Update stock
            bookStock.setBookAvailable(bookStock.getBookAvailable() - 1);
            bookStock.setUpdatedBy(username);
            bookStock.setUpdatedAt(now);
            bookStockRepository.save(bookStock);

            reservationStatus = loanType.getCode();
        }

        return BookReservationResponse.builder()
            .bookTitle(book.getTitle())
            .AuthorName(book.getAuthor().getName())
            .ReservationDate(now)
            .status(reservationStatus)
            .build();
    }

    @Transactional
    public BookReservationResponse cancelReserveBookById(Long bookId, Authentication authentication) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();
        
        String username = authentication.getName();

        // Step 1: Get current user
        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Step 2: Find active, not-loaned, not-cancelled reservation
        ReservationEntity reservation = reservationRepository
            .findByBookIdAndUserIdAndIsLoanedFalseAndIsCancelledFalse(bookId, currentUser.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Active reservation not found"));

        // Step 3: Fetch CANCELLED transaction type
        MasterTransactionTypeEntity cancelType = transactionTypeRepository.findByCode("CANCELLED")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type CANCELLED not found"));

        // Step 4: Create cancel transaction
        TransactionEntity cancelTransaction = TransactionEntity.builder()
            .book(reservation.getBook())
            .user(currentUser)
            .transactionDate(new Date())
            .transactionType(cancelType)
            .createdBy(username)
            .createdAt(new Date())
            .updatedBy(username)
            .updatedAt(new Date())
            .build();
        transactionRepository.save(cancelTransaction);

        // Step 5: Mark reservation as cancelled
        reservation.setIsCancelled(true);
        reservation.setUpdatedAt(new Date());
        reservation.setUpdatedBy(username);
        reservationRepository.save(reservation);

        // Step 6: Response
        return BookReservationResponse.builder()
            .bookTitle(reservation.getBook().getTitle())
            .AuthorName(reservation.getBook().getAuthor().getName())
            .ReservationDate(reservation.getReservationDate())
            .build();
    }


}

