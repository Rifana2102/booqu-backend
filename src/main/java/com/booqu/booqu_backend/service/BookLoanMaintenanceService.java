package com.booqu.booqu_backend.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.BookEntity;
import com.booqu.booqu_backend.entity.BookStockEntity;
import com.booqu.booqu_backend.entity.LoanEntity;
import com.booqu.booqu_backend.entity.MasterTransactionTypeEntity;
import com.booqu.booqu_backend.entity.ReservationEntity;
import com.booqu.booqu_backend.entity.TransactionEntity;
import com.booqu.booqu_backend.repository.BookRepository;
import com.booqu.booqu_backend.repository.BookStockRepository;
import com.booqu.booqu_backend.repository.LoanRepository;
import com.booqu.booqu_backend.repository.ReservationRepository;
import com.booqu.booqu_backend.repository.TransactionRepository;
import com.booqu.booqu_backend.repository.TransactionTypeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookLoanMaintenanceService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;
    private final TransactionRepository transactionRepository;
    private final ReservationRepository reservationRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    @Transactional
    public void processAllAutoReturnAndReservations() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        MasterTransactionTypeEntity transactionTypeLoan = transactionTypeRepository
            .findByCode("LOAN")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type LOAN not found"));

        MasterTransactionTypeEntity transactionTypeReturned = transactionTypeRepository
            .findByCode("RETURNED")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type RETURNED not found"));

        List<BookEntity> allBooks = bookRepository.findAll();

        for (BookEntity book : allBooks) {
            Optional<BookStockEntity> optionalStock = bookStockRepository.findById(book.getId());
            if (optionalStock.isEmpty()) continue;

            BookStockEntity stock = optionalStock.get();

            // Step 1: Auto-return overdue loans
            List<LoanEntity> overdueLoans = loanRepository
                .findByBookIdAndDueDateAndIsReturnedFalse(book.getId(), yesterday);

            for (LoanEntity loan : overdueLoans) {
                loan.setReturned(true);
                loan.setReturnDate(LocalDate.now());
                loan.setUpdatedBy("SYSTEM");
                loan.setUpdatedAt(new Date());
                loanRepository.save(loan);

                // Increase stock
                stock.setBookAvailable(stock.getBookAvailable() + 1);

                TransactionEntity tx = TransactionEntity.builder()
                    .book(book)
                    .user(loan.getUser())
                    .transactionDate(new Date())
                    .transactionType(transactionTypeReturned)
                    .createdAt(new Date())
                    .createdBy("SYSTEM")
                    .updatedAt(new Date())
                    .updatedBy("SYSTEM")
                    .build();
                transactionRepository.save(tx);
            }

            // Step 2: Fulfill reservations if stock is available
            List<ReservationEntity> reservations = reservationRepository
                .findAllByBookIdAndIsLoanedFalseAndIsCancelledFalseOrderByReservationDateAsc(book.getId());

            for (ReservationEntity reservation : reservations) {
                if (stock.getBookAvailable() == null || stock.getBookAvailable() <= 0) break;

                LocalDate dueDate = LocalDate.now().plusDays(5);

                LoanEntity loan = LoanEntity.builder()
                    .book(book)
                    .user(reservation.getUser())
                    .loanDate(LocalDate.now())
                    .dueDate(dueDate)
                    .isReturned(false)
                    .createdBy("SYSTEM")
                    .createdAt(new Date())
                    .updatedBy("SYSTEM")
                    .updatedAt(new Date())
                    .build();
                loanRepository.save(loan);

                stock.setBookAvailable(stock.getBookAvailable() - 1);
                stock.setUpdatedBy("SYSTEM");
                stock.setUpdatedAt(new Date());

                reservation.setIsLoaned(true);
                reservation.setUpdatedBy("SYSTEM");
                reservation.setUpdatedAt(new Date());
                reservationRepository.save(reservation);

                TransactionEntity tx = TransactionEntity.builder()
                    .book(book)
                    .user(reservation.getUser())
                    .transactionDate(new Date())
                    .transactionType(transactionTypeLoan)
                    .createdAt(new Date())
                    .createdBy("SYSTEM")
                    .updatedAt(new Date())
                    .updatedBy("SYSTEM")
                    .build();
                transactionRepository.save(tx);
            }

            bookStockRepository.save(stock);
        }
    }
}