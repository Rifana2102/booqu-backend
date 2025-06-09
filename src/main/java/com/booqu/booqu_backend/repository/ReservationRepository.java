package com.booqu.booqu_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booqu.booqu_backend.entity.BookEntity;
import com.booqu.booqu_backend.entity.ReservationEntity;
import com.booqu.booqu_backend.entity.UserEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>{
    Optional<ReservationEntity> findFirstByBookIdAndIsLoanedFalseOrderByReservationDateAsc(Long bookId);
    
    List<ReservationEntity> findByBookIdAndIsLoanedFalseOrderByReservationDateAsc(Long bookId);

    boolean existsByUserAndBookAndIsLoanedFalseAndIsCancelledFalse(UserEntity currentUser, BookEntity book);

    List<ReservationEntity> findAllByBookIdAndIsLoanedFalseAndIsCancelledFalseOrderByReservationDateAsc(Long bookId);

    Optional<ReservationEntity> findByBookIdAndUserIdAndIsLoanedFalseAndIsCancelledFalse(Long bookId, Long userId);
    
}

