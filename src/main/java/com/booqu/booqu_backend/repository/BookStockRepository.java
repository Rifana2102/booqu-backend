package com.booqu.booqu_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.BookStockEntity;

@Repository
public interface BookStockRepository extends JpaRepository<BookStockEntity, Long> {
}
