package com.example.dbisolation.repository;

import com.example.dbisolation.model.ProductPessimistic;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductPessimisticRepository extends JpaRepository<ProductPessimistic, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT p FROM ProductPessimistic p WHERE p.id = :id")
    Optional<ProductPessimistic> findByIdWithPessimisticReadLock(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductPessimistic p WHERE p.id = :id")
    Optional<ProductPessimistic> findByIdWithPessimisticWriteLock(Long id);
}
