package com.booqu.booqu_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.BookEntity;
import com.booqu.booqu_backend.entity.LoanEntity;
import com.booqu.booqu_backend.entity.UserEntity;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByBookIdAndDueDateBeforeAndIsReturnedFalse(Long bookId, LocalDate date);
    boolean existsByUserAndBookAndIsReturnedFalse(UserEntity user, BookEntity book);
    List<LoanEntity> findByBookIdAndDueDateAndIsReturnedFalse(Long bookId, LocalDate yesterday);
    Optional<LoanEntity> findByUserAndBookAndIsReturnedFalse(UserEntity user, BookEntity book);
}