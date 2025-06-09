package com.booqu.booqu_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.TransactionEntity;
import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.model.notification.NotificationResponse;
import com.booqu.booqu_backend.model.profile.UserProfileResponse;
import com.booqu.booqu_backend.repository.TransactionRepository;
import com.booqu.booqu_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookLoanMaintenanceService bookLoanMaintenanceService;

    public List<NotificationResponse> getNotification(Authentication authentication) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        String username = authentication.getName();

        UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        List<TransactionEntity> transactions = transactionRepository.findAllByUserOrderByTransactionDateDesc(currentUser);

        return transactions.stream()
            .map(tx -> {
                String type = tx.getTransactionType().getCode();
                String bookName = tx.getBook().getTitle();
                String description;

                switch (type) {
                    case "LOAN":
                        description = "Book has been borrowed";
                        break;
                    case "RETURNED":
                        description = "Book has been returned";
                        break;
                    case "RESERVED":
                        description = "Book is being processed for reservation";
                        break;
                    case "CANCELLED":
                        description = "Book reservation was cancelled";
                        break;
                    default:
                        description = "Unknown transaction";
                }

                return NotificationResponse.builder()
                    .status(type)
                    .bookName(bookName)
                    .description(description)
                    .transactionDate(tx.getTransactionDate())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
