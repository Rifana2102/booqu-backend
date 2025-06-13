package com.booqu.booqu_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.mapper.ResponseMapper;
import com.booqu.booqu_backend.model.book.BookDetailResponse;
import com.booqu.booqu_backend.model.book.BooksResponse;
import com.booqu.booqu_backend.repository.BookRepository;
import com.booqu.booqu_backend.repository.UserRepository;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookLoanMaintenanceService bookLoanMaintenanceService;

    public List<BooksResponse> getAllBooks(String authorCode, String genreCode, String keyword) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        String[] genreCodes = (genreCode == null || genreCode.isEmpty())
        ? new String[] {}
        : genreCode.split(",");

        // Call the repository
        List<Object[]> rawResult = bookRepository.findBooksWithFilter(authorCode, genreCodes, keyword);
        return ResponseMapper.ToBookResponseListMapper(rawResult);
    }

    public BookDetailResponse getBookById(Long id, boolean isLogin) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        Object[] row = bookRepository.findBookWithGenresById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        return ResponseMapper.ToBookResponseMapper(row, isLogin);
    }

    public List<BooksResponse> getFavoriteLoanBooks() {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        List<Object[]> rawFavorites = bookRepository.findFavoriteLoanBooks();
        return ResponseMapper.ToBookResponseListMapper(rawFavorites);
    }

    public List<BooksResponse> getFavoriteAuthorBooks() {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        List<Object[]> rawFavorites = bookRepository.findFavoriteAuthorsBooks();
        return ResponseMapper.ToBookResponseListMapper(rawFavorites);
    }

    public List<BooksResponse> getMyLoanBooks(Authentication authentication) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        String username = authentication.getName();

        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<Object[]> rawFavorites = bookRepository.findActiveLoanBooksByUser(currentUser.getId());
        return ResponseMapper.ToBookResponseListMapper(rawFavorites);
    }

    public List<BooksResponse> getMyReservedBooks(Authentication authentication) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        String username = authentication.getName();

        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<Object[]> rows = bookRepository.findReservedBooksNotYetLoanedByUser(currentUser.getId());
        return ResponseMapper.ToBookResponseListMapper(rows);
    }

    public List<BooksResponse> getMyBookHistory(Authentication authentication) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();
        
        String username = authentication.getName();

        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<Object[]> rows = bookRepository.findBookTransactionHistoryByUser(currentUser.getId());
        return ResponseMapper.ToBookResponseListMapper(rows);
    }
}