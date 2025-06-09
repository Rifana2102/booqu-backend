package com.booqu.booqu_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.TransactionEntity;
import com.booqu.booqu_backend.entity.UserEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("""
        SELECT t.book.id 
        FROM TransactionEntity t
        WHERE t.transactionType.code = 'LOAN'
        GROUP BY t.book.id
        ORDER BY COUNT(t.id) DESC
    """)
    List<Long> findMostBorrowedBookIds();

    List<TransactionEntity> findAllByUserOrderByTransactionDateDesc(UserEntity user);
}
