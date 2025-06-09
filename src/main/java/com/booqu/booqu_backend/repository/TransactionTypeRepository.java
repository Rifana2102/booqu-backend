package com.booqu.booqu_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.MasterTransactionTypeEntity;

@Repository
public interface TransactionTypeRepository extends JpaRepository<MasterTransactionTypeEntity, Long> {
    Optional<MasterTransactionTypeEntity> findByCode(String code);
}