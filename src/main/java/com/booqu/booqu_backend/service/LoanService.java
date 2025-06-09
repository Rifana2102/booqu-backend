package com.booqu.booqu_backend.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.booqu.booqu_backend.mapper.ResponseMapper;
import com.booqu.booqu_backend.model.book.BookLoanResponse;
import com.booqu.booqu_backend.repository.BookRepository;
import com.booqu.booqu_backend.repository.BookStockRepository;
import com.booqu.booqu_backend.repository.LoanRepository;
import com.booqu.booqu_backend.repository.ReservationRepository;
import com.booqu.booqu_backend.repository.TransactionRepository;
import com.booqu.booqu_backend.repository.TransactionTypeRepository;
import com.booqu.booqu_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanService {

     @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private BookLoanMaintenanceService bookLoanMaintenanceService;

    @Transactional
    public BookLoanResponse loanBookById(Long bookId, Authentication authentication) {
        String username = authentication.getName();

        // Step 1: Auto return and process reservations for all books system-wide
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        // Step 2: Fetch authenticated user
        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Step 3: Fetch book and stock
        BookEntity book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        BookStockEntity bookStock = bookStockRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book stock not found"));

        // Step 4: Check if current user already has an active loan for this book
        boolean hasActiveLoan = loanRepository.existsByUserAndBookAndIsReturnedFalse(currentUser, book);
        if (hasActiveLoan) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have an active loan for this book");
        }

        // Step 5: Check if stock is still available (after auto return + reservation fulfillment)
        if (bookStock.getBookAvailable() == null || bookStock.getBookAvailable() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is currently unavailable due to reservations or low stock");
        }

        // Step 6: Proceed to create loan for the current user
        LocalDate dueDate = LocalDate.now().plusDays(5);

        LoanEntity loan = LoanEntity.builder()
            .book(book)
            .user(currentUser)
            .loanDate(LocalDate.now())
            .dueDate(dueDate)
            .isReturned(false)
            .createdBy(username)
            .createdAt(new Date())
            .updatedBy(username)
            .updatedAt(new Date())
            .build();
        loanRepository.save(loan);

        // Step 7: Record the loan transaction
        MasterTransactionTypeEntity transactionTypeLoan = transactionTypeRepository.findByCode("LOAN")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type LOAN not found"));

        TransactionEntity transactionLoanEntity = TransactionEntity.builder()
            .book(book)
            .user(currentUser)
            .transactionDate(new Date())
            .transactionType(transactionTypeLoan)
            .createdAt(new Date())
            .createdBy(username)
            .updatedAt(new Date())
            .updatedBy(username)
            .build();
        transactionRepository.save(transactionLoanEntity);

        // Step 8: Update stock
        bookStock.setBookAvailable(bookStock.getBookAvailable() - 1);
        bookStock.setUpdatedBy(username);
        bookStock.setUpdatedAt(new Date());
        bookStockRepository.save(bookStock);

        // Step 9: Return response
        return BookLoanResponse.builder()
            .bookTitle(book.getTitle())
            .authorName(book.getAuthor().getName())
            .loanDate(LocalDate.now())
            .dueDate(dueDate)
            .build();
    }

    @Transactional
    public BookLoanResponse returnLoanBookById(Long bookId, Authentication authentication) {
         String username = authentication.getName();

        // Step 1: Auto return and process reservations for all books system-wide
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        // Step 2: Fetch authenticated user
        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Step 3: Fetch book and stock
        BookEntity book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        BookStockEntity bookStock = bookStockRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Stock not found"));

        LoanEntity activeLoan = loanRepository.findByUserAndBookAndIsReturnedFalse(currentUser, book)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No active loan found for this book"));
        
        activeLoan.setReturned(true);
        activeLoan.setReturnDate(LocalDate.now());
        activeLoan.setUpdatedAt(new Date());
        activeLoan.setUpdatedBy(username);
        loanRepository.save(activeLoan);

        bookStock.setBookAvailable(bookStock.getBookAvailable() + 1);
        activeLoan.setUpdatedAt(new Date());
        activeLoan.setUpdatedBy("SYSTEM");
        bookStockRepository.save(bookStock);

        MasterTransactionTypeEntity transactionTypeReturned = transactionTypeRepository.findByCode("RETURNED")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction type RETURNED not found"));

        TransactionEntity transactionLoanEntity = TransactionEntity.builder()
            .book(book)
            .user(currentUser)
            .transactionDate(new Date())
            .transactionType(transactionTypeReturned)
            .createdAt(new Date())
            .createdBy(username)
            .updatedAt(new Date())
            .updatedBy(username)
            .build();
        transactionRepository.save(transactionLoanEntity);

         return BookLoanResponse.builder()
            .bookTitle(book.getTitle())
            .authorName(book.getAuthor().getName())
            .loanDate(activeLoan.getLoanDate())
            .dueDate(activeLoan.getDueDate())
            .returnDate(activeLoan.getReturnDate())
            .build();
    }

}
